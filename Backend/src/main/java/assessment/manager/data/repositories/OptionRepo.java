package assessment.manager.data.repositories;

import assessment.manager.data.models.Option;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OptionRepo extends MongoRepository<Option, String> {
}
