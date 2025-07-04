package assessment.manager.security.jwtToken;

import java.util.Optional;

public class AssessmentServiceImpl implements AssessmentService {
    private final AssessmentRespository assessmentRespository;

    public AssessmentServiceImpl(AssessmentRespository assessmentRespository) {
        this.assessmentRespository = assessmentRespository;
    }

    @Override
    public void saveToken(AssessmentToken testingToken) {
        assessmentRespository.save(testingToken);

    }

    @Override
    public Optional<AssessmentToken> getValidTokenByAnyToken(String accessToken) {

        return assessmentRespository.findGeoJwtTokenByAccessToken( accessToken );
    }

    @Override
    public void revokeToken(String accessToken) {
        final AssessmentToken assessmentToken= getValidTokenByAnyToken( accessToken )
                .orElse(null);
        if(assessmentToken!=null){
            assessmentToken.setRevoked(true);
            assessmentRespository.save(assessmentToken);
        }

    }
}
