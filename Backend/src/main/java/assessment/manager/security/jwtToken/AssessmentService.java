package assessment.manager.security.jwtToken;

import java.util.Optional;

public interface AssessmentService {
    void saveToken(AssessmentToken testingToken);
    Optional<AssessmentToken> getValidTokenByAnyToken(String anyToken);
    void revokeToken(String accessToken);
}
