package assessment.manager.services.interfaces;

import assessment.manager.data.models.Assessment;
import assessment.manager.dtos.requests.*;
import assessment.manager.dtos.responses.AssessmentListResponse;
import assessment.manager.dtos.responses.AssessmentResponse;
import assessment.manager.dtos.responses.GradeResponse;

import java.util.List;
import java.util.Optional;

public interface AssessmentService {
    AssessmentResponse createAssessment(CreateAssessmentRequest request);
    AssessmentListResponse viewAssessmentsCreated(String creatorId);
    GradeResponse autoGrade(AutoGradeRequest request);
    void setTimer(SetTimerRequest request);
    void randomizeQuestions(RandomizeQuestionsRequest request);
    void randomizeOptions(RandomizeOptionsRequest request);
    void setCorrectAnswer(SetCorrectAnswerRequest request);
    
    // Additional utility methods
    AssessmentResponse getAssessmentById(String assessmentId);
    void deleteAssessment(String assessmentId);
    void addQuestionToAssessment(String assessmentId, String questionId);
    void removeQuestionFromAssessment(String assessmentId, String questionId);
}
