package assessment.manager.services.interfaces;

import assessment.manager.data.models.Profile;
import assessment.manager.data.models.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> getById(String id);
    User updateProfile(String userId, Profile profile);
}
