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

    @PostMapping("/sync")
    public ResponseEntity<?> syncCreditLensData(@RequestBody CreditLensRequestDTO request) {
        log.info("Request to sync CreditLens data with payload: {}", request);

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

            log.info("Processing request for BAMS ID: {}", request.getSubmissionInput().getBamsId());

            // Fetch and save data using the new service method with full request
            CreditLensResponse response = creditLensService.fetchAndSaveCreditLensData(request);

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


    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", message);
        errorResponse.put("timestamp", java.time.LocalDateTime.now());
        return errorResponse;
    }
}