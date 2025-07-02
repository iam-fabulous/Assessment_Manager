package assessment.manager.data.models;

import java.time.LocalDate;
import java.util.List;

public class Profile {
    private String userId;
    private String bio;
    private String phoneNumber;
    private String gender;
    private String institution;
    private String profileImageUrl;
    private LocalDate dateOfBirth;
    private List<String> qualifications;
    private List<Assessment> assessmentsCreated;
    private List<Assessment> assessmentsTaken;
    private List<String> Experiences;
}
