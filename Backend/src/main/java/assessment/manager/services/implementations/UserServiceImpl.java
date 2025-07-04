package assessment.manager.services.implementations;

import assessment.manager.data.models.User;
import assessment.manager.data.repositories.UserRepo;
import assessment.manager.dto.request.LoginRequest;
import assessment.manager.dto.request.RegisterRequest;
import assessment.manager.dto.request.VerifyEmailRequest;
import assessment.manager.dto.response.LoginResponse;
import assessment.manager.dto.response.OtpVerificationResponse;
import assessment.manager.enums.UserRole;
import assessment.manager.opt.OtpModel;
import assessment.manager.opt.OtpServiceImpl;
import assessment.manager.opt.mailRequest.MailServiceImpl;
import assessment.manager.security.jwt.JwtResponse;
import assessment.manager.security.jwt.JwtTokenServiceImpl;
import assessment.manager.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
//@Autowired
//    private final MailService mailService;

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpServiceImpl otpServiceImpl;
    private MailServiceImpl mailService;
    private final MailServiceImpl mailServiceImpl;
    private final JwtTokenServiceImpl jwtTokenServiceImpl;



    @Autowired
    public UserServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder, OtpServiceImpl otpServiceImpl, MailServiceImpl mailServiceImpl, JwtTokenServiceImpl jwtTokenServiceImpl) {
//        this.mailService = mailService;

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpServiceImpl = otpServiceImpl;
        this.mailServiceImpl = mailServiceImpl;
        this.jwtTokenServiceImpl = jwtTokenServiceImpl;
    }

    @Override
    public User register(RegisterRequest registerRequest) {
//        if (adminUser.getRole() != UserRole.ADMIN) {
//            throw new RuntimeException("Only ADMIN can register new users.");
//        }

        if (registerRequest.getRole() == UserRole.ADMIN) {
            throw new RuntimeException("Cannot register another ADMIN.");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists.");
        }

        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(registerRequest.getRole());

        return userRepository.save(newUser);
    }

    private void checkIfUserExistsByEmail(String email) {
       boolean isPresent = userRepository.findByEmail( email ).isPresent( );
      if(isPresent){
          User user = userRepository.findByEmail(email).get();
          if(!user.isEnabled());
          resendVerificationEmail( email );
          throw new RuntimeException("User already exists.");
      }
    }
    private void sendVerificationEmail(User user, String otp){
        String mailTemplate = AppUtilities.GET_EMAIL_VERIFICATION_MAIL_TEMPLATE;

        String email = user.getEmail();
        String subject = "Email Verification";
        String htmlContent = String.format(mailTemplate,  otp);
//        mailService.sendMail(email, subject, htmlContent);

//        String htmlContent   = String.format( mailTemplate, name ,otp) mailTemplate.replace("{name}", name).replace("{otp}", otp);

    }

    @Override
    public String resendVerificationEmail(String  email) {
        User user = getUserByEmail( email );
        String otp = otpServiceImpl.generateAndSaveOtp(user);
        sendVerificationEmail(user, otp);



        return "Another otp has been sent to your email address. ";
    }

    @Override
    public OtpVerificationResponse verifyOtp(VerifyEmailRequest verifyEmailRequest) {
        Long userId = Long.parseLong( verifyEmailRequest.getUserId().trim() );
        OtpModel otpModel = otpServiceImpl.validateReceivedOtp(verifyEmailRequest.getOtp(),userId);
        User user = otpModel.getUser();
        if(!user.isEnabled()){
            user.setEnabled(true);
            User savedUser = userRepository.save(user);
            otpServiceImpl.deleteAllOtpsForUser( otpModel );
            return getOtpVerificationResponse(savedUser);

        }


        return null;
    }
    private OtpVerificationResponse getOtpVerificationResponse(User user){
        JwtResponse jwtResponse = jwtTokenServiceImpl.getJwtTokenResponse(user);
        return OtpVerificationResponse.builder()
                .email(user.getEmail())
                .jwtResponse(jwtResponse)
                .isEnabled(user.isEnabled())
                .build();


    }
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = jwtTokenServiceImpl.authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        JwtResponse jwtResponse = jwtTokenServiceImpl.getJwtTokenResponse(user);
        return LoginResponse.builder()
                .message("Login successful")
                .jwtResponse(jwtResponse)
                .build();
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }


}