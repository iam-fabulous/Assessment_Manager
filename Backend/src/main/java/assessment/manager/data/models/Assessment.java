package assessment.manager.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
public class Assessment {
    @Id
    private String id;
    private String title;
    private String description;
    private String creatorId;
    private List<String> questionIds;
    private boolean randomizeOptions;
    private boolean randomizeQuestions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
