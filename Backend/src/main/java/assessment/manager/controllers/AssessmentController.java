package assessment.manager.controllers;

import assessment.manager.dtos.requests.*;
import assessment.manager.dtos.responses.*;
import assessment.manager.services.interfaces.AssessmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST Controller for managing assessments
 * 
 * This controller provides endpoints for:
 * - Creating new assessments
 * - Retrieving assessments by creator
 * - Auto-grading assessment submissions
 * - Managing assessment settings (timer, randomization, correct answers)
 * 
 * @author Assessment Management System
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/assessments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AssessmentController {

    private final AssessmentService assessmentService;

    /**
     * Create a new assessment
     */
    @PostMapping
    public ResponseEntity<AssessmentResponse> createAssessment(
            @Valid @RequestBody CreateAssessmentRequest request) {
        
        log.info("Creating new assessment with title: {}", request.getTitle());
        
        try {
            AssessmentResponse response = assessmentService.createAssessment(request);
            log.info("Assessment created successfully with ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating assessment: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all assessments created by a specific user
     */
    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<AssessmentListResponse> getAssessmentsByCreator(
            @PathVariable String creatorId) {
        
        log.info("Fetching assessments for creator: {}", creatorId);
        
        try {
            AssessmentListResponse response = assessmentService.viewAssessmentsCreated(creatorId);
            
            if (response != null) {
                log.info("Found {} assessments for creator: {}", 
                    response.getAssessments() != null ? response.getAssessments().size() : 0, 
                    creatorId);
                return ResponseEntity.ok(response);
            } else {
                log.warn("No assessments found for creator: {}", creatorId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error fetching assessments for creator {}: {}", creatorId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Auto-grade an assessment submission
     */
    @PostMapping("/grade")
    public ResponseEntity<GradeResponse> autoGrade(
            @Valid @RequestBody AutoGradeRequest request) {
        
        log.info("Auto-grading assessment with ID: {}", request.getAssessmentId());
        
        try {
            GradeResponse response = assessmentService.autoGrade(request);
            
            if (response != null) {
                log.info("Assessment graded successfully. Grade: {}", response.getGrade());
                return ResponseEntity.ok(response);
            } else {
                log.warn("Unable to grade assessment with ID: {}", request.getAssessmentId());
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            log.error("Error grading assessment {}: {}", request.getAssessmentId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Set or update timer for an assessment
     */
    @PutMapping("/timer")
    public ResponseEntity<Void> setTimer(
            @Valid @RequestBody SetTimerRequest request) {
        
        log.info("Setting timer for assessment: {} to {} minutes", 
                request.getAssessmentId(), request.getDurationMinutes());
        
        try {
            assessmentService.setTimer(request);
            log.info("Timer set successfully for assessment: {}", request.getAssessmentId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error setting timer for assessment {}: {}", 
                    request.getAssessmentId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Enable/disable question randomization for an assessment
     */
    @PutMapping("/randomize-questions")
    public ResponseEntity<Void> randomizeQuestions(
            @Valid @RequestBody RandomizeQuestionsRequest request) {
        
        log.info("Setting question randomization for assessment: {}", request.getAssessmentId());
        
        try {
            assessmentService.randomizeQuestions(request);
            log.info("Question randomization updated successfully for assessment: {}", 
                    request.getAssessmentId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating question randomization for assessment {}: {}", 
                    request.getAssessmentId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Enable/disable option randomization for an assessment
     */
    @PutMapping("/randomize-options")
    public ResponseEntity<Void> randomizeOptions(
            @Valid @RequestBody RandomizeOptionsRequest request) {
        
        log.info("Setting option randomization for assessment: {}", request.getAssessmentId());
        
        try {
            assessmentService.randomizeOptions(request);
            log.info("Option randomization updated successfully for assessment: {}", 
                    request.getAssessmentId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating option randomization for assessment {}: {}", 
                    request.getAssessmentId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Set the correct answer for a question in an assessment
     */
    @PutMapping("/correct-answer")
    public ResponseEntity<Void> setCorrectAnswer(
            @Valid @RequestBody SetCorrectAnswerRequest request) {
        
        log.info("Setting correct answer for question: {} to option: {}", 
                request.getQuestionId(), request.getOptionId());
        
        try {
            assessmentService.setCorrectAnswer(request);
            log.info("Correct answer set successfully for question: {}", request.getQuestionId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error setting correct answer for question {}: {}", 
                    request.getQuestionId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get a specific assessment by ID
     */
    @GetMapping("/{assessmentId}")
    public ResponseEntity<AssessmentResponse> getAssessmentById(
            @PathVariable String assessmentId) {
        
        log.info("Fetching assessment with ID: {}", assessmentId);
        
        try {
            AssessmentResponse response = assessmentService.getAssessmentById(assessmentId);
            log.info("Assessment fetched successfully: {}", assessmentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching assessment {}: {}", assessmentId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Delete an assessment
     */
    @DeleteMapping("/{assessmentId}")
    public ResponseEntity<Void> deleteAssessment(
            @PathVariable String assessmentId) {
        
        log.info("Deleting assessment with ID: {}", assessmentId);
        
        try {
            assessmentService.deleteAssessment(assessmentId);
            log.info("Assessment deleted successfully: {}", assessmentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting assessment {}: {}", assessmentId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Add a question to an assessment
     */
    @PostMapping("/{assessmentId}/questions/{questionId}")
    public ResponseEntity<Void> addQuestionToAssessment(
            @PathVariable String assessmentId,
            @PathVariable String questionId) {
        
        log.info("Adding question {} to assessment {}", questionId, assessmentId);
        
        try {
            assessmentService.addQuestionToAssessment(assessmentId, questionId);
            log.info("Question added successfully to assessment: {}", assessmentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error adding question {} to assessment {}: {}", questionId, assessmentId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Remove a question from an assessment
     */
    @DeleteMapping("/{assessmentId}/questions/{questionId}")
    public ResponseEntity<Void> removeQuestionFromAssessment(
            @PathVariable String assessmentId,
            @PathVariable String questionId) {
        
        log.info("Removing question {} from assessment {}", questionId, assessmentId);
        
        try {
            assessmentService.removeQuestionFromAssessment(assessmentId, questionId);
            log.info("Question removed successfully from assessment: {}", assessmentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error removing question {} from assessment {}: {}", questionId, assessmentId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
