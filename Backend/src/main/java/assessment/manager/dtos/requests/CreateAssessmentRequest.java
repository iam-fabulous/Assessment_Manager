package assessment.manager.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssessmentRequest {
    private String creatorId;
    private String title;
    private String description;
    private int timerDuration;
    private boolean randomizeOptions;
    private boolean randomizeQuestions;
}
