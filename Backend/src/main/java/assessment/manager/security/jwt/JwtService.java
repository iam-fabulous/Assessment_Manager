package assessment.manager.security.jwt;

import assessment.manager.data.models.User;

public interface JwtService {
    User authenticate(String email, String password);
    JwtResponse getJwtTokenResponse (User user);
}
