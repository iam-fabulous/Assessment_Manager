package assessment.manager.services.interfaces;

import assessment.manager.data.models.User;
import assessment.manager.dto.RegisterRequest;

public interface UserService {
    User register(RegisterRequest registerRequest);
}
