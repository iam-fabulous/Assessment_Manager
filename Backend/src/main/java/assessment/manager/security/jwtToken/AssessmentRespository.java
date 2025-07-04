package assessment.manager.security.jwtToken;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AssessmentRespository   extends MongoRepository<AssessmentToken,Long> {
    Optional<AssessmentToken> findGeoJwtTokenByAccessToken(String accessToken);
}
