package com.restTemp.InterService.Controller;

import com.restTemp.InterService.Dto.CreditLensResponse;
import com.restTemp.InterService.Service.CreditLensMockService;
import com.restTemp.InterService.Service.CreditLensService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
@Profile("test")
public class CreditLensTestController {

    private final CreditLensMockService mockService;
    private final CreditLensService creditLensService;

    @PostMapping("/sync/{bamsId}")
    public ResponseEntity<CreditLensResponse> testSyncWithMockData(@PathVariable Integer bamsId) {
        log.info("TEST: Syncing CreditLens data with mock data for BAMS ID: {}", bamsId);

        try {
            // Get mock data
            CreditLensResponse mockResponse = mockService.getMockCreditLensData(bamsId);

            // Save to database
            creditLensService.saveCreditLensData(mockResponse);

            log.info("TEST: Successfully synced mock data for BAMS ID: {}", bamsId);
            return ResponseEntity.status(HttpStatus.CREATED).body(mockResponse);

        } catch (Exception e) {
            log.error("TEST: Error syncing mock data for BAMS ID: {}", bamsId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}