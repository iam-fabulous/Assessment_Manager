package assessment.manager.security.jwt;

import assessment.manager.data.models.User;
import assessment.manager.security.jwtToken.AssessmentToken;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenServiceImpl implements JwtService{
    @Override
    public User authenticate(String email, String password) {
        return null;
    }

    @Override
    public JwtResponse getJwtTokenResponse(User user) {
//       final String email = user.getEmail();
//       final  String accessToken = jwtService.generateAccessToken(email);
//        final  String refreshToken = jwtService.generateRefreshToken(email);
//        saveRefreshToken(user, accessToken);
//
//        return JwtResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
        return null;
    }
//    private void saveRefreshToken(User user, String accessToken){
//      final  AssessmentToken assessmentToken = AssessmentToken.builder()
//              .accessToken(accessToken)
//              .refreshToken()
//
//              .isExpired(false)
//              .isRevoked(false)
//              .build();
//    }
}
