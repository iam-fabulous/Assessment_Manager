package assessment.manager.data.repositories;

import assessment.manager.data.models.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepo extends MongoRepository<Question, String> {

}
