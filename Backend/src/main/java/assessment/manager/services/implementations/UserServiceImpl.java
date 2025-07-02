package assessment.manager.services.implementations;


import assessment.manager.data.models.Profile;
import assessment.manager.data.models.User;
import assessment.manager.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public User registerUser(User user) {
        return null;
    }

    @Override
    public Optional<User> getById(String id) {
        return Optional.empty();
    }

    @Override
    public User updateProfile(String userId, Profile profile) {
        return null;
    }
}
