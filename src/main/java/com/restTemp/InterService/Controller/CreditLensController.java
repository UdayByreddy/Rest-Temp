package com.restTemp.InterService.Controller;

import com.restTemp.InterService.Dto.CreditLensResponse;
import com.restTemp.InterService.Service.CreditLensService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/creditlens")
@RequiredArgsConstructor
@Slf4j
public class CreditLensController {

    private final CreditLensService creditLensService;

    @PostMapping("/sync/{bamsId}")
    public ResponseEntity<CreditLensResponse> syncCreditLensData(@PathVariable Integer bamsId) {
        log.info("Request to sync CreditLens data for BAMS ID: {}", bamsId);

        try {
            CreditLensResponse response = creditLensService.fetchAndSaveCreditLensData(bamsId);
            log.info("Successfully synced CreditLens data for BAMS ID: {}", bamsId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            log.error("Error syncing CreditLens data for BAMS ID: {}", bamsId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
}
