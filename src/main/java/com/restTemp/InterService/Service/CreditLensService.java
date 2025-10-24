package com.restTemp.InterService.Service;

import com.restTemp.InterService.Dto.*;
import com.restTemp.InterService.Entity.*;
import com.restTemp.InterService.Helper.Helper;
import com.restTemp.InterService.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditLensService {

    private final RestTemplate restTemplate;
    private final AccountDetailsRepository accountDetailsRepository;
    private final BamsScoringRepository bamsScoringRepository;
    private final Helper helper;

    @Value("${creditlens.api.url}")
    private String creditLensApiUrl;

    @Value("${creditlens.api.key}")
    private String apiKey;


    public CreditLensResponse fetchCreditLensDataFromAPI(CreditLensRequestDTO request) {
        log.info("Fetching CreditLens data from external Boomi API with request: {}", request);

        try {
            // Build headers with x-api-key
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (apiKey != null && !apiKey.isEmpty()) {
                headers.set("x-api-key", apiKey);
                log.debug("Added x-api-key to request headers");
            }

            // Create HTTP entity with the full request body
            HttpEntity<CreditLensRequestDTO> entity = new HttpEntity<>(request, headers);

            log.info("Calling Boomi API at URL: {}", creditLensApiUrl);

            // Make POST request to Boomi API
            ResponseEntity<CreditLensResponse> response = restTemplate.exchange(
                    creditLensApiUrl,
                    HttpMethod.POST,
                    entity,
                    CreditLensResponse.class
            );

            log.info("Successfully fetched CreditLens data from Boomi API");
            return response.getBody();

        } catch (Exception e) {
            log.error("Error fetching CreditLens data from Boomi API: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch CreditLens data from API: " + e.getMessage(), e);
        }
    }


    public CreditLensResponse fetchCreditLensDataFromAPI(Integer bamsId) {
        log.info("Fetching CreditLens data using legacy method for BAMS ID: {}", bamsId);

        // Create request DTO from bamsId
        CreditLensRequestDTO request = createRequestFromBamsId(bamsId);

        return fetchCreditLensDataFromAPI(request);
    }

    @Transactional
    public void saveCreditLensData(CreditLensResponse response) {
        log.info("Saving CreditLens data to database");

        try {
            // Save Account Details with Statements
            if (response.getAccountDetailsOutput() != null) {
                helper.saveAccountDetails(response.getAccountDetailsOutput());
            }

            // Save BAMS Scoring with all nested data
            if (response.getBamsScoringOutput() != null) {
                saveBamsScoring(response.getBamsScoringOutput());
            }

            log.info("Successfully saved CreditLens data to database");

        } catch (Exception e) {
            log.error("Error saving CreditLens data to database", e);
            throw new RuntimeException("Failed to save CreditLens data: " + e.getMessage(), e);
        }
    }


    @Transactional
    public CreditLensResponse fetchAndSaveCreditLensData(CreditLensRequestDTO request) {
        log.info("Fetching and saving CreditLens data with request payload");

        CreditLensResponse response = fetchCreditLensDataFromAPI(request);
        saveCreditLensData(response);

        log.info("Successfully fetched and saved CreditLens data");
        return response;
    }


    @Transactional
    public CreditLensResponse fetchAndSaveCreditLensData(Integer bamsId) {
        log.info("Fetching and saving CreditLens data for BAMS ID: {}", bamsId);

        CreditLensRequestDTO request = createRequestFromBamsId(bamsId);
        return fetchAndSaveCreditLensData(request);
    }

    public Optional<AccountDetails> getAccountByBamsId(Integer bamsId) {
        log.info("Retrieving account details for BAMS ID: {}", bamsId);
        return accountDetailsRepository.findByBamsId(bamsId);
    }


    public List<AccountDetails> getAllAccounts() {
        log.info("Retrieving all accounts");
        return accountDetailsRepository.findAll();
    }

    public Optional<BamsScoring> getBamsScoringByBamsId(String bamsId) {
        log.info("Retrieving BAMS scoring for BAMS ID: {}", bamsId);
        return bamsScoringRepository.findByBamsId(bamsId);
    }

    /**
     * Check if account exists by BAMS ID
     */
    public boolean accountExistsByBamsId(Integer bamsId) {
        return accountDetailsRepository.existsByBamsId(bamsId);
    }

    /**
     * Check if BAMS scoring exists by BAMS ID
     */
    public boolean bamsScoringExistsByBamsId(String bamsId) {
        return bamsScoringRepository.existsByBamsId(bamsId);
    }

    /**
     * Helper method to create request DTO from bamsId (for backward compatibility)
     */
    private CreditLensRequestDTO createRequestFromBamsId(Integer bamsId) {
        ScoreRequestInputDTO scoreRequestInput = new ScoreRequestInputDTO();
        scoreRequestInput.setScoreRequestId(String.valueOf(System.currentTimeMillis()));
        scoreRequestInput.setTransactionTimeStamp(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        scoreRequestInput.setSourceSystem("Surety_Bams");

        SubmissionInputDTO submissionInput = new SubmissionInputDTO();
        submissionInput.setBamsId(String.valueOf(bamsId));
        submissionInput.setCreditLensId("");
        submissionInput.setStatementDate("");

        CreditLensRequestDTO request = new CreditLensRequestDTO();
        request.setScoreRequestInput(scoreRequestInput);
        request.setSubmissionInput(submissionInput);

        return request;
    }

    /**
     * Save BamsScoring with all nested data
     */
    private BamsScoring saveBamsScoring(BamsScoringOutputDTO dto) {
        log.debug("Saving BamsScoring for BAMS ID: {}", dto.getBamsId());

        // Find existing or create new
        BamsScoring bamsScoring = bamsScoringRepository
                .findByBamsId(dto.getBamsId())
                .orElse(new BamsScoring());

        // Map basic fields
        bamsScoring.setAccountName(dto.getAccountName());
        bamsScoring.setAccountType(dto.getAccountType());
        bamsScoring.setBamsId(dto.getBamsId());
        bamsScoring.setBranchName(dto.getBranchName());
        bamsScoring.setBusinessType(dto.getBusinessType());
        bamsScoring.setScoreStatus(dto.getScoreStatus());

        // Save scoring statements
        if (dto.getStatements() != null && !dto.getStatements().isEmpty()) {
            List<ScoringStatement> scoringStatements = new ArrayList<>();

            for (ScoringStatementDTO stmtDto : dto.getStatements()) {
                ScoringStatement scoringStatement = createScoringStatement(stmtDto, bamsScoring);
                scoringStatements.add(scoringStatement);
            }

            bamsScoring.setScoringStatements(scoringStatements);
        }

        bamsScoring = bamsScoringRepository.save(bamsScoring);
        log.debug("Successfully saved BamsScoring for BAMS ID: {}", dto.getBamsId());

        return bamsScoring;
    }

    /**
     * Create ScoringStatement with nested data
     */
    private ScoringStatement createScoringStatement(ScoringStatementDTO dto, BamsScoring bamsScoring) {
        ScoringStatement scoringStatement = new ScoringStatement();

        scoringStatement.setAccountStatus(dto.getAccountStatus());
        scoringStatement.setPreparedBy(dto.getPreparedBy());
        scoringStatement.setStatementDate(parseDateTime(dto.getStatementDate()));
        scoringStatement.setStmtType(dto.getStmtType());
        scoringStatement.setBamsScoring(bamsScoring);

        // Create and set Financial Metric
        if (dto.getFinancialMetric() != null) {
            FinancialMetric financialMetric = createFinancialMetric(dto.getFinancialMetric(), scoringStatement);
            scoringStatement.setFinancialMetric(financialMetric);
        }

        // Create and set Scoring Output
        if (dto.getScoringOutput() != null) {
            ScoringOutput scoringOutput = createScoringOutput(dto.getScoringOutput(), scoringStatement);
            scoringStatement.setScoringOutput(scoringOutput);
        }

        return scoringStatement;
    }

    /**
     * Create FinancialMetric
     */
    private FinancialMetric createFinancialMetric(FinancialMetricDTO dto, ScoringStatement scoringStatement) {
        FinancialMetric metric = new FinancialMetric();

        metric.setDebtToNetWorth(parseBigDecimal(dto.getDebtToNetWorth()));
        metric.setDebtToNetWorthColor(dto.getDebtToNetWorthColor());
        metric.setFreeCashFlow(parseBigDecimal(dto.getFreeCashFlow()));
        metric.setFreeCashFlowColor(dto.getFreeCashFlowColor());
        metric.setIbdToNetWorth(parseBigDecimal(dto.getIbdToNetWorth()));
        metric.setIbdToNetWorthColor(dto.getIbdToNetWorthColor());
        metric.setInterestCoverage(parseBigDecimal(dto.getInterestCoverage()));
        metric.setInterestCoverageColor(dto.getInterestCoverageColor());
        metric.setNetProfitMargin(parseBigDecimal(dto.getNetProfitMargin()));
        metric.setNetProfitMarginColor(dto.getNetProfitMarginColor());
        metric.setOperatingMargin(parseBigDecimal(dto.getOperatingMargin()));
        metric.setOperatingMarginColor(dto.getOperatingMarginColor());
        metric.setProfitabilityRatio(parseBigDecimal(dto.getProfitabilityRatio()));
        metric.setProfitabilityRatioColor(dto.getProfitabilityRatioColor());
        metric.setScoringStatement(scoringStatement);

        return metric;
    }

    /**
     * Create ScoringOutput with CategoryOutputs
     */
    private ScoringOutput createScoringOutput(ScoringOutputDTO dto, ScoringStatement scoringStatement) {
        ScoringOutput scoringOutput = new ScoringOutput();

        scoringOutput.setFutureNetCashFlow(dto.getFutureNetCashFlow());
        scoringOutput.setRiskScale(dto.getRiskScale());
        scoringOutput.setRiskScaleColor(dto.getRiskScaleColor());
        scoringOutput.setTotalScore(dto.getTotalScore());
        scoringOutput.setTotalScoreColor(dto.getTotalScoreColor());
        scoringOutput.setTotalWorkProgram(dto.getTotalWorkProgram());
        scoringOutput.setScoringStatement(scoringStatement);

        // Create CategoryOutputs
        if (dto.getCategoryOutput() != null && !dto.getCategoryOutput().isEmpty()) {
            List<CategoryOutput> categoryOutputs = new ArrayList<>();

            for (CategoryOutputDTO catDto : dto.getCategoryOutput()) {
                CategoryOutput categoryOutput = createCategoryOutput(catDto, scoringOutput);
                categoryOutputs.add(categoryOutput);
            }

            scoringOutput.setCategoryOutputs(categoryOutputs);
        }

        return scoringOutput;
    }

    /**
     * Create CategoryOutput with VariableOutputs
     */
    private CategoryOutput createCategoryOutput(CategoryOutputDTO dto, ScoringOutput scoringOutput) {
        CategoryOutput categoryOutput = new CategoryOutput();

        categoryOutput.setCategoryName(dto.getCategoryName());
        categoryOutput.setCategoryScore(dto.getCategoryScore());
        categoryOutput.setCategoryScoreColor(dto.getCategoryScoreColor());
        categoryOutput.setScoringOutput(scoringOutput);

        // Create VariableOutputs
        if (dto.getVariableOutput() != null && !dto.getVariableOutput().isEmpty()) {
            List<VariableOutput> variableOutputs = new ArrayList<>();

            for (VariableOutputDTO varDto : dto.getVariableOutput()) {
                VariableOutput variableOutput = createVariableOutput(varDto, categoryOutput);
                variableOutputs.add(variableOutput);
            }

            categoryOutput.setVariableOutputs(variableOutputs);
        }

        return categoryOutput;
    }

    /**
     * Create VariableOutput
     */
    private VariableOutput createVariableOutput(VariableOutputDTO dto, CategoryOutput categoryOutput) {
        VariableOutput vo = new VariableOutput();

        // Liquidity variables
        vo.setAccountsReceivableTurnoverRatio(parseBigDecimal(dto.getAccountsReceivableTurnoverRatio()));
        vo.setAccountsReceivableTurnoverScore(parseBigDecimal(dto.getAccountsReceivableTurnoverScore()));
        vo.setCurrentAssetsByCurrentLiabilitiesRatio(parseBigDecimal(dto.getCurrentAssetsByCurrentLiabilitiesRatio()));
        vo.setCurrentAssetsByCurrentLiabilitiesScore(parseBigDecimal(dto.getCurrentAssetsByCurrentLiabilitiesScore()));
        vo.setNetCashByTotalAssetsRatio(parseBigDecimal(dto.getNetCashByTotalAssetsRatio()));
        vo.setNetCashByTotalAssetsScore(parseBigDecimal(dto.getNetCashByTotalAssetsScore()));
        vo.setUnderbillingsByWorkingCapitalRatio(parseBigDecimal(dto.getUnderbillingsByWorkingCapitalRatio()));
        vo.setUnderbillingsByWorkingCapitalScore(parseBigDecimal(dto.getUnderbillingsByWorkingCapitalScore()));

        // Leverage variables
        vo.setCurrentBankDebtByTotalAssetsRatio(parseBigDecimal(dto.getCurrentBankDebtByTotalAssetsRatio()));
        vo.setCurrentBankDebtByTotalAssetsScore(parseBigDecimal(dto.getCurrentBankDebtByTotalAssetsScore()));
        vo.setTotalBankDebtByTotalAssetsRatio(parseBigDecimal(dto.getTotalBankDebtByTotalAssetsRatio()));
        vo.setTotalBankDebtByTotalAssetsScore(parseBigDecimal(dto.getTotalBankDebtByTotalAssetsScore()));
        vo.setTotalLiabilitiesByEquityRatio(parseBigDecimal(dto.getTotalLiabilitiesByEquityRatio()));
        vo.setTotalLiabilitiesByEquityScore(parseBigDecimal(dto.getTotalLiabilitiesByEquityScore()));
        vo.setWorkingCapitalByCurrentAssetsRatio(parseBigDecimal(dto.getWorkingCapitalByCurrentAssetsRatio()));
        vo.setWorkingCapitalByCurrentAssetsScore(parseBigDecimal(dto.getWorkingCapitalByCurrentAssetsScore()));

        // Operational variables
        vo.setGrossProfitMarginRatio(parseBigDecimal(dto.getGrossProfitMarginRatio()));
        vo.setGrossProfitMarginScore(parseBigDecimal(dto.getGrossProfitMarginScore()));
        vo.setNetProfitMarginRatio(parseBigDecimal(dto.getNetProfitMarginRatio()));
        vo.setNetProfitMarginScore(parseBigDecimal(dto.getNetProfitMarginScore()));
        vo.setNetWorthByWorkProgramRatio(parseBigDecimal(dto.getNetWorthByWorkProgramRatio()));
        vo.setNetWorthByWorkProgramScore(parseBigDecimal(dto.getNetWorthByWorkProgramScore()));
        vo.setOverheadByOperatingIncomeRatio(parseBigDecimal(dto.getOverheadByOperatingIncomeRatio()));
        vo.setOverheadByOperatingIncomeScore(parseBigDecimal(dto.getOverheadByOperatingIncomeScore()));
        vo.setWorkingCapitalByWorkProgramRatio(parseBigDecimal(dto.getWorkingCapitalByWorkProgramRatio()));
        vo.setWorkingCapitalByWorkProgramScore(parseBigDecimal(dto.getWorkingCapitalByWorkProgramScore()));

        vo.setCategoryOutput(categoryOutput);

        return vo;
    }

    // ============================================
    // Utility Methods
    // ============================================

    /**
     * Parse String to BigDecimal, handling nulls and formatting
     */
    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            // Remove commas and parse
            String cleanValue = value.replace(",", "").trim();
            return new BigDecimal(cleanValue);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse BigDecimal from value: '{}', error: {}", value, e.getMessage());
            return null;
        }
    }

    /**
     * Parse String to LocalDateTime with multiple format support
     */
    private LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        try {
            // Format: "2024-01-31 00:00:00.000 -0800"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            try {
                // Format: "2024-01-31 00:00:00"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (DateTimeParseException e2) {
                log.warn("Failed to parse DateTime from value: '{}', error: {}", dateTimeString, e2.getMessage());
                return null;
            }
        }
    }
}