package assessment.manager.opt;

import assessment.manager.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository  extends MongoRepository<OtpModel, Long> {

    OtpModel findByOtpAndUser_Id( String otp,Long userId);

    OtpModel findOtpModelByUser(User user);
}
