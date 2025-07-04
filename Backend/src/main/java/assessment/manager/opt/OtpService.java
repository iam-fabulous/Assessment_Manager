package assessment.manager.opt;

import assessment.manager.data.models.User;

public interface OtpService {
    String generateAndSaveOtp(User user);



    void deleteAllOtpsForUser(OtpModel userId);

    OtpModel validateReceivedOtp(String receivedOtp, Long userId);


}
