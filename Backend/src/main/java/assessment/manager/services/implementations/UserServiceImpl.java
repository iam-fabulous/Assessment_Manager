package assessment.manager.services.implementations;

import assessment.manager.data.models.User;
import assessment.manager.data.repositories.UserRepo;
import assessment.manager.dto.RegisterRequest;
import assessment.manager.enums.UserRole;
import assessment.manager.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}