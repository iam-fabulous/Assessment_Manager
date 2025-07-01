package assessment.manager.data.models;

import assessment.manager.enums.UserRole;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private LocalDateTime createdAt;
}
