package assessment.manager.dto.response;

import assessment.manager.security.jwt.JwtResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private String message;
    private JwtResponse jwtResponse;
}
