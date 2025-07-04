package assessment.manager.services.interfaces;

import assessment.manager.data.models.User;
import assessment.manager.dto.request.LoginRequest;
import assessment.manager.dto.request.RegisterRequest;
import assessment.manager.dto.request.VerifyEmailRequest;
import assessment.manager.dto.response.LoginResponse;
import assessment.manager.dto.response.OtpVerificationResponse;

public interface UserService {
    User register(RegisterRequest registerRequest);
    String  resendVerificationEmail(String email);
    OtpVerificationResponse verifyOtp(VerifyEmailRequest verifyEmailRequest);

    LoginResponse login (LoginRequest loginRequest);

    User getUserByEmail(String email);

}
