package assessment.manager.services.interfaces;

import assessment.manager.data.models.Assessment;

import java.util.List;
import java.util.Optional;

public interface AssessmentService {
    Assessment createAssessment(Assessment assessment);
    List<Assessment> getAll();
    Optional<Assessment> getById(String id);
}
