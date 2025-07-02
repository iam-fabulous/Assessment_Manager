package assessment.manager.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Option {
    @Id
    private String id;
    private String text;
}
