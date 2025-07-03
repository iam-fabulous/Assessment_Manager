package assessment.manager.services.implementations;

import assessment.manager.data.models.Assessment;
import assessment.manager.data.models.Option;
import assessment.manager.data.models.Question;
import assessment.manager.data.repositories.AssessmentRepo;
import assessment.manager.data.repositories.OptionRepo;
import assessment.manager.data.repositories.QuestionRepo;
import assessment.manager.dtos.requests.*;
import assessment.manager.dtos.responses.*;
import assessment.manager.services.interfaces.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl  implements AssessmentService {
private final AssessmentRepo assessmentRepo;
private final QuestionRepo questionRepo;
private final OptionRepo optionRepo;

    @Override
    public AssessmentResponse createAssessment(CreateAssessmentRequest request) {
        Assessment assessment = Assessment.builder()
                .id(null)
                .creatorId(request.getCreatorId())
                .title(request.getTitle())
                .description(request.getDescription())
                .questionIds(new ArrayList<>())
                .timerDuration(request.getTimerDuration())
                .isOptionsRandomize(request.isRandomizeOptions())
                .isQuestionsRandomize(request.isRandomizeQuestions())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Assessment savedAssessment = assessmentRepo.save(assessment);
        return convertToResponse(savedAssessment);
    }

    @Override
    public AssessmentListResponse viewAssessmentsCreated(String creatorId) {
        try {
            List<Assessment> assessments = assessmentRepo.findByCreatorId(creatorId);
            
            List<AssessmentResponse> assessmentResponses = assessments.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
            
            return AssessmentListResponse.builder()
                    .assessments(assessmentResponses)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching assessments for creator: " + creatorId, e);
        }
    }

    @Override
    public GradeResponse autoGrade(AutoGradeRequest request) {
        try {
            Assessment assessment = assessmentRepo.findById(request.getAssessmentId())
                    .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + request.getAssessmentId()));
            
            int totalQuestions = assessment.getQuestionIds().size();
            int correctAnswers = 0;
            
            // Grade each answered question
            for (AnsweredQuestionRequest answeredQuestion : request.getAnsweredQuestions()) {
                Question question = getQuestionById(answeredQuestion.getQuestionId());
                
                // Check if the selected answer is correct
                if (question.getCorrectOptionId() != null && 
                    question.getCorrectOptionId().equals(answeredQuestion.getSelectedOptionId())) {
                    correctAnswers++;
                }
            }
            
            // Calculate grade as percentage
            int grade = totalQuestions > 0 ? (correctAnswers * 100) / totalQuestions : 0;
            
            return GradeResponse.builder()
                    .grade(grade)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error grading assessment: " + request.getAssessmentId(), e);
        }
    }

    @Override
    public void setTimer(SetTimerRequest request) {
        try {
            Assessment assessment = assessmentRepo.findById(request.getAssessmentId())
                    .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + request.getAssessmentId()));
            
            // Convert minutes to seconds for storage
            long timerDurationInSeconds = request.getDurationMinutes() * 60;
            
            Assessment updatedAssessment = Assessment.builder()
                    .id(assessment.getId())
                    .creatorId(assessment.getCreatorId())
                    .title(assessment.getTitle())
                    .description(assessment.getDescription())
                    .questionIds(assessment.getQuestionIds())
                    .timerDuration(timerDurationInSeconds)
                    .isOptionsRandomize(assessment.isOptionsRandomize())
                    .isQuestionsRandomize(assessment.isQuestionsRandomize())
                    .createdAt(assessment.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            assessmentRepo.save(updatedAssessment);
        } catch (Exception e) {
            throw new RuntimeException("Error setting timer for assessment: " + request.getAssessmentId(), e);
        }
    }

    @Override
    public void randomizeQuestions(RandomizeQuestionsRequest request) {
        try {
            Assessment assessment = assessmentRepo.findById(request.getAssessmentId())
                    .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + request.getAssessmentId()));
            
            // Toggle question randomization
            Assessment updatedAssessment = Assessment.builder()
                    .id(assessment.getId())
                    .creatorId(assessment.getCreatorId())
                    .title(assessment.getTitle())
                    .description(assessment.getDescription())
                    .questionIds(assessment.getQuestionIds())
                    .timerDuration(assessment.getTimerDuration())
                    .isOptionsRandomize(assessment.isOptionsRandomize())
                    .isQuestionsRandomize(!assessment.isQuestionsRandomize()) // Toggle randomization
                    .createdAt(assessment.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            assessmentRepo.save(updatedAssessment);
        } catch (Exception e) {
            throw new RuntimeException("Error updating question randomization for assessment: " + request.getAssessmentId(), e);
        }
    }

    @Override
    public void randomizeOptions(RandomizeOptionsRequest request) {
        try {
            Assessment assessment = assessmentRepo.findById(request.getAssessmentId())
                    .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + request.getAssessmentId()));
            
            // Toggle option randomization
            Assessment updatedAssessment = Assessment.builder()
                    .id(assessment.getId())
                    .creatorId(assessment.getCreatorId())
                    .title(assessment.getTitle())
                    .description(assessment.getDescription())
                    .questionIds(assessment.getQuestionIds())
                    .timerDuration(assessment.getTimerDuration())
                    .isOptionsRandomize(!assessment.isOptionsRandomize()) // Toggle randomization
                    .isQuestionsRandomize(assessment.isQuestionsRandomize())
                    .createdAt(assessment.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            assessmentRepo.save(updatedAssessment);
        } catch (Exception e) {
            throw new RuntimeException("Error updating option randomization for assessment: " + request.getAssessmentId(), e);
        }
    }

    @Override
    public void setCorrectAnswer(SetCorrectAnswerRequest request) {
        try {
            Question question = getQuestionById(request.getQuestionId());
            
            // Verify that the option exists and belongs to this question
            if (!question.getOptionIds().contains(request.getOptionId())) {
                throw new RuntimeException("Option ID " + request.getOptionId() + 
                    " does not belong to question ID " + request.getQuestionId());
            }
            
            // Verify the option exists in the database
            getOptionById(request.getOptionId());
            
            // Update the question with the correct answer
            Question updatedQuestion = Question.builder()
                    .id(question.getId())
                    .text(question.getText())
                    .optionIds(question.getOptionIds())
                    .correctOptionId(request.getOptionId())
                    .tags(question.getTags())
                    .build();
            
            questionRepo.save(updatedQuestion);
        } catch (Exception e) {
            throw new RuntimeException("Error setting correct answer for question: " + request.getQuestionId(), e);
        }
    }


    private AssessmentResponse convertToResponse(Assessment assessment) {
        return AssessmentResponse.builder()
                .id(assessment.getId())
                .creatorId(assessment.getCreatorId())
                .title(assessment.getTitle())
                .description(assessment.getDescription())
                .questions(assessment.getQuestionIds().stream()
                        .map(this::getQuestionById)
                        .map(this::convertToQuestionResponse)
                        .collect(Collectors.toList()))
                .timerDuration(assessment.getTimerDuration())
                .isQuestionsRandomized(assessment.isQuestionsRandomize())
                .isOptionsRandomized(assessment.isOptionsRandomize())
                .createdAt(assessment.getCreatedAt())
                .updatedAt(assessment.getUpdatedAt())
                .build();
    }

    private QuestionResponse convertToQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .text(question.getText())
                .options(question.getOptionIds().stream()
                        .map(this::getOptionById)
                        .map(this::convertToOptionResponse)
                        .collect(Collectors.toList()))
                .correctOptionId(question.getCorrectOptionId())
                .tags(question.getTags())
                .build();
    }

    private OptionResponse convertToOptionResponse(Option option) {
        return OptionResponse.builder()
                .id(option.getId())
                .text(option.getText())
                .build();
    }

    private Question getQuestionById(String id) {
        return questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));
    }

    private Option getOptionById(String id) {
        return optionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found with ID: " + id));
    }

    /**
     * Additional utility method to get assessment by ID
     * This can be used by the controller for single assessment retrieval
     */
    public AssessmentResponse getAssessmentById(String assessmentId) {
        try {
            Assessment assessment = assessmentRepo.findById(assessmentId)
                    .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));
            return convertToResponse(assessment);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching assessment: " + assessmentId, e);
        }
    }

    /**
     * Additional utility method to delete assessment by ID
     * This can be used by the controller for assessment deletion
     */
    public void deleteAssessment(String assessmentId) {
        try {
            if (!assessmentRepo.existsById(assessmentId)) {
                throw new RuntimeException("Assessment not found with ID: " + assessmentId);
            }
            assessmentRepo.deleteById(assessmentId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting assessment: " + assessmentId, e);
        }
    }

    /**
     * Additional utility method to add a question to an assessment
     */
    public void addQuestionToAssessment(String assessmentId, String questionId) {
        try {
            Assessment assessment = assessmentRepo.findById(assessmentId)
                    .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));
            
            // Verify the question exists
            getQuestionById(questionId);
            
            // Add question if not already present
            if (!assessment.getQuestionIds().contains(questionId)) {
                List<String> updatedQuestionIds = new ArrayList<>(assessment.getQuestionIds());
                updatedQuestionIds.add(questionId);
                
                Assessment updatedAssessment = Assessment.builder()
                        .id(assessment.getId())
                        .creatorId(assessment.getCreatorId())
                        .title(assessment.getTitle())
                        .description(assessment.getDescription())
                        .questionIds(updatedQuestionIds)
                        .timerDuration(assessment.getTimerDuration())
                        .isOptionsRandomize(assessment.isOptionsRandomize())
                        .isQuestionsRandomize(assessment.isQuestionsRandomize())
                        .createdAt(assessment.getCreatedAt())
                        .updatedAt(LocalDateTime.now())
                        .build();
                
                assessmentRepo.save(updatedAssessment);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error adding question to assessment: " + assessmentId, e);
        }
    }

    /**
     * Additional utility method to remove a question from an assessment
     */
    public void removeQuestionFromAssessment(String assessmentId, String questionId) {
        try {
            Assessment assessment = assessmentRepo.findById(assessmentId)
                    .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));
            
            // Remove question if present
            if (assessment.getQuestionIds().contains(questionId)) {
                List<String> updatedQuestionIds = new ArrayList<>(assessment.getQuestionIds());
                updatedQuestionIds.remove(questionId);
                
                Assessment updatedAssessment = Assessment.builder()
                        .id(assessment.getId())
                        .creatorId(assessment.getCreatorId())
                        .title(assessment.getTitle())
                        .description(assessment.getDescription())
                        .questionIds(updatedQuestionIds)
                        .timerDuration(assessment.getTimerDuration())
                        .isOptionsRandomize(assessment.isOptionsRandomize())
                        .isQuestionsRandomize(assessment.isQuestionsRandomize())
                        .createdAt(assessment.getCreatedAt())
                        .updatedAt(LocalDateTime.now())
                        .build();
                
                assessmentRepo.save(updatedAssessment);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error removing question from assessment: " + assessmentId, e);
        }
    }

}
