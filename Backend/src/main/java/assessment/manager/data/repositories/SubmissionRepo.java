package assessment.manager.data.repositories;

import assessment.manager.data.models.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubmissionRepo extends MongoRepository<Submission, String> {
    List<Submission> findByParticipantId(String participantId);
    List<Submission> findByAssessmentId(String assessmentId);
}
