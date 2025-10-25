package com.restTemp.InterService.Controller;

import com.restTemp.InterService.Dto.CreditLensRequestDTO;
import com.restTemp.InterService.Dto.CreditLensResponse;
import com.restTemp.InterService.Service.CreditLensService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/creditlens")
@RequiredArgsConstructor
@Slf4j
public class CreditLensController {

    private final CreditLensService creditLensService;

    @PostMapping("/trigger")
    public ResponseEntity<?> triggerCreditLensSync(@RequestBody CreditLensRequestDTO request) {
        log.info("Request to trigger CreditLens API call with payload: {}", request);

        try {
            // Validate request structure
            if (request.getSubmissionInput() == null) {
                log.error("SubmissionInput is missing in the request");
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("SubmissionInput is required"));
            }

            if (request.getSubmissionInput().getBamsId() == null ||
                    request.getSubmissionInput().getBamsId().trim().isEmpty()) {
                log.error("BAMS ID is missing in the request");
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("BAMS ID is required in SubmissionInput"));
            }

            // Validate bamsId format
            try {
                Integer.parseInt(request.getSubmissionInput().getBamsId().trim());
            } catch (NumberFormatException e) {
                log.error("Invalid BAMS ID format: {}", request.getSubmissionInput().getBamsId(), e);
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Invalid BAMS ID format. Must be a valid integer."));
            }

            log.info("Triggering API call for BAMS ID: {}", request.getSubmissionInput().getBamsId());

            // Trigger external API call with callback URL
            Map<String, Object> ackResponse = creditLensService.triggerCreditLensAPICall(request);

            log.info("API call triggered. Returning acknowledgment for BAMS ID: {}",
                    request.getSubmissionInput().getBamsId());

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ackResponse);

        } catch (RuntimeException e) {
            log.error("Error triggering CreditLens API call: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Failed to trigger API call: " + e.getMessage()));

        } catch (Exception e) {
            log.error("Unexpected error triggering CreditLens API call", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("An unexpected error occurred"));
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<?> handleCreditLensCallback(@RequestBody CreditLensResponse response) {
        log.info("Received callback from external API with response data");

        try {
            // Validate response
            if (response == null) {
                log.error("Callback response is null");
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Response data is required"));
            }

            log.info("Processing callback and saving data to database");

            // Process and save the response data
            Map<String, Object> result = creditLensService.processCallbackAndSave(response);

            log.info("Successfully processed callback and saved data");
            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) {
            log.error("Invalid callback data: {}", e.getMessage(), e);
            return ResponseEntity.badRequest()
                    .body(createErrorResponse("Invalid callback data: " + e.getMessage()));

        } catch (RuntimeException e) {
            log.error("Error processing callback: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Failed to process callback: " + e.getMessage()));

        } catch (Exception e) {
            log.error("Unexpected error processing callback", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("An unexpected error occurred while processing callback"));
        }
    }

    @PostMapping("/sync")
    public ResponseEntity<?> syncCreditLensData(@RequestBody CreditLensRequestDTO request) {
        log.info("Request to sync CreditLens data (synchronous) with payload: {}", request);

        try {
            // Validate request structure
            if (request.getSubmissionInput() == null) {
                log.error("SubmissionInput is missing in the request");
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("SubmissionInput is required"));
            }

            if (request.getSubmissionInput().getBamsId() == null ||
                    request.getSubmissionInput().getBamsId().trim().isEmpty()) {
                log.error("BAMS ID is missing in the request");
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("BAMS ID is required in SubmissionInput"));
            }

            // Validate bamsId format
            try {
                Integer.parseInt(request.getSubmissionInput().getBamsId().trim());
            } catch (NumberFormatException e) {
                log.error("Invalid BAMS ID format: {}", request.getSubmissionInput().getBamsId(), e);
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Invalid BAMS ID format. Must be a valid integer."));
            }

            log.info("Processing synchronous request for BAMS ID: {}", request.getSubmissionInput().getBamsId());

            // Fetch and save data synchronously
            CreditLensResponse response = creditLensService.fetchCreditLensDataFromAPI(request);
            creditLensService.saveCreditLensData(response);

            log.info("Successfully synced CreditLens data for BAMS ID: {}", request.getSubmissionInput().getBamsId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            log.error("Error syncing CreditLens data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Failed to sync CreditLens data: " + e.getMessage()));

        } catch (Exception e) {
            log.error("Unexpected error syncing CreditLens data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("An unexpected error occurred"));
        }
    }

    /**
     * Get account details by BAMS ID
     */
    @GetMapping("/accounts/{bamsId}")
    public ResponseEntity<?> getAccountByBamsId(@PathVariable Integer bamsId) {
        log.info("Request to get account details for BAMS ID: {}", bamsId);

        try {
            return creditLensService.getAccountByBamsId(bamsId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            log.error("Error getting account for BAMS ID: {}", bamsId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get BAMS scoring by BAMS ID
     */
    @GetMapping("/scoring/{bamsId}")
    public ResponseEntity<?> getBamsScoringByBamsId(@PathVariable String bamsId) {
        log.info("Request to get BAMS scoring for BAMS ID: {}", bamsId);

        try {
            return creditLensService.getBamsScoringByBamsId(bamsId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

        } catch (Exception e) {
            log.error("Error getting BAMS scoring for BAMS ID: {}", bamsId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Check processing status (optional - useful for client to poll status)
     */
    @GetMapping("/status/{bamsId}")
    public ResponseEntity<?> checkProcessingStatus(@PathVariable String bamsId) {
        log.info("Request to check processing status for BAMS ID: {}", bamsId);

        try {
            boolean accountExists = creditLensService.accountExistsByBamsId(Integer.parseInt(bamsId));
            boolean scoringExists = creditLensService.bamsScoringExistsByBamsId(bamsId);

            Map<String, Object> statusResponse = new HashMap<>();
            statusResponse.put("bamsId", bamsId);
            statusResponse.put("accountDataExists", accountExists);
            statusResponse.put("scoringDataExists", scoringExists);
            statusResponse.put("status", (accountExists && scoringExists) ? "COMPLETED" : "PROCESSING");
            statusResponse.put("timestamp", java.time.LocalDateTime.now());

            return ResponseEntity.ok(statusResponse);

        } catch (NumberFormatException e) {
            log.error("Invalid BAMS ID format: {}", bamsId, e);
            return ResponseEntity.badRequest()
                    .body(createErrorResponse("Invalid BAMS ID format"));

        } catch (Exception e) {
            log.error("Error checking status for BAMS ID: {}", bamsId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Helper method to create error responses
     */

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", message);
        errorResponse.put("timestamp", java.time.LocalDateTime.now());
        return errorResponse;
    }
}