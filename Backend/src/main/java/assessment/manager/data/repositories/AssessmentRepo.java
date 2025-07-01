package assessment.manager.data.repositories;

import assessment.manager.data.models.Assessment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssessmentRepo extends MongoRepository<Assessment, String> {
    List<Assessment> findByCreatorId(String creatorId);
}
