package assessment.manager.dto.response;

import assessment.manager.security.jwt.JwtResponse;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class OtpVerificationResponse {
    private String name;
    private String email;
    private boolean isEnabled;
    private JwtResponse jwtResponse;
}
