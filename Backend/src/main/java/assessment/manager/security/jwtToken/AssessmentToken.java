package assessment.manager.security.jwtToken;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Getter
public class AssessmentToken {
    @Id
    @Indexed(unique = true)
    private String accessToken;

    @Indexed(unique = true)
    private String refreshToken;

    private boolean isRevoked;
    private boolean isExpired;
}
