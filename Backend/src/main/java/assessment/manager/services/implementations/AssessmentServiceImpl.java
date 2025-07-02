package assessment.manager.services.implementations;

import assessment.manager.data.models.Assessment;
import assessment.manager.services.interfaces.AssessmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentServiceImpl  implements AssessmentService {

    @Override
    public Assessment createAssessment(Assessment assessment) {
        return null;
    }

    @Override
    public List<Assessment> getAll() {
        return List.of();
    }

    @Override
    public Optional<Assessment> getById(String id) {
        return Optional.empty();
    }
}
