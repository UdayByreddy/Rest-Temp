package com.restTemp.InterService.Service;

import com.restTemp.InterService.Dto.*;
import com.restTemp.InterService.Dto.CreditLensResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Profile("test")
public class CreditLensMockService {

    public CreditLensResponse getMockCreditLensData(Integer bamsId) {
        log.info("Returning mock CreditLens data for BAMS ID: {}", bamsId);

        CreditLensResponse response = new CreditLensResponse();

        // Mock Account Details
        AccountDetailsOutputDTO accountDetails = new AccountDetailsOutputDTO();
        accountDetails.setAccountName("Guy Hopkins Construction Co., Inc.");
        accountDetails.setAccountNumber("11781");
        accountDetails.setAccountType("Contract");
        accountDetails.setBamsId(bamsId);
        accountDetails.setBrokerAgencyName("Surety Bond Solutions, LLC");
        accountDetails.setBusinessType("GC");
        accountDetails.setCity("Baton Rouge");
        accountDetails.setState("LA");
        accountDetails.setZip("70809");
        accountDetails.setCreditLensId(73);
        accountDetails.setStreetAddress1("13855 W. Amber Avenue");
        accountDetails.setStreetAddress2("");
        accountDetails.setUnderwriterName("Charles Recer");
        accountDetails.setProducerName("Surety Bond Solutions, LLC");
        accountDetails.setTenureOfTheAccount("18");

        // Mock Statements
        List<StatementOutputDTO> statements = new ArrayList<>();
        StatementOutputDTO stmt1 = new StatementOutputDTO();
        stmt1.setCash("1069134");
        stmt1.setCashColor("blue");
        stmt1.setNetWorth("2118415");
        stmt1.setNetWorthColor("blue");
        stmt1.setRevenueSize("14120856");
        stmt1.setStatementDate("2024-01-31 00:00:00.000 -0800");
        statements.add(stmt1);

        StatementOutputDTO stmt2 = new StatementOutputDTO();
        stmt2.setCash("1771164");
        stmt2.setCashColor("blue");
        stmt2.setNetWorth("1992143");
        stmt2.setNetWorthColor("blue");
        stmt2.setRevenueSize("12843135");
        stmt2.setStatementDate("2023-12-31 00:00:00.000 -0800");
        statements.add(stmt2);

        accountDetails.setStatementOutputs(statements);
        response.setAccountDetailsOutput(accountDetails);

        // Mock BAMS Scoring
        BamsScoringOutputDTO bamsScoring = new BamsScoringOutputDTO();
        bamsScoring.setAccountName("Guy Hopkins Construction Co. Inc.");
        bamsScoring.setAccountType("Contract");
        bamsScoring.setBamsId(String.valueOf(bamsId));
        bamsScoring.setBranchName("Dallas");
        bamsScoring.setBusinessType("GC");
        bamsScoring.setScoreStatus("Completed");

        // Mock Scoring Statements
        List<ScoringStatementDTO> scoringStatements = new ArrayList<>();
        ScoringStatementDTO scoringStmt = new ScoringStatementDTO();
        scoringStmt.setAccountStatus("Lost");
        scoringStmt.setPreparedBy("In House Accountant");
        scoringStmt.setStatementDate("2024-01-31 00:00:00");
        scoringStmt.setStmtType("Interim");

        // Mock Financial Metric
        FinancialMetricDTO financialMetric = new FinancialMetricDTO();
        financialMetric.setDebtToNetWorth("1.81");
        financialMetric.setDebtToNetWorthColor("yellow");
        financialMetric.setFreeCashFlow("345323.00");
        financialMetric.setIbdToNetWorth("0.00");
        financialMetric.setIbdToNetWorthColor("dark green");
        financialMetric.setNetProfitMargin("0.02");
        financialMetric.setNetProfitMarginColor("light red");
        financialMetric.setOperatingMargin("0.08");
        financialMetric.setOperatingMarginColor("yellow");
        financialMetric.setProfitabilityRatio("2.37");
        financialMetric.setProfitabilityRatioColor("dark green");
        scoringStmt.setFinancialMetric(financialMetric);

        // Mock Scoring Output
        ScoringOutputDTO scoringOutput = new ScoringOutputDTO();
        scoringOutput.setFutureNetCashFlow("1M");
        scoringOutput.setRiskScale("Average");
        scoringOutput.setRiskScaleColor("yellow");
        scoringOutput.setTotalScore("50.75");
        scoringOutput.setTotalScoreColor("yellow");
        scoringOutput.setTotalWorkProgram("25M");

        // Mock Category Outputs
        List<CategoryOutputDTO> categoryOutputs = new ArrayList<>();

        // Liquidity Category
        CategoryOutputDTO liquidityCategory = new CategoryOutputDTO();
        liquidityCategory.setCategoryName("Liquidity");
        liquidityCategory.setCategoryScore("14.75");
        liquidityCategory.setCategoryScoreColor("light red");

        List<VariableOutputDTO> liquidityVariables = new ArrayList<>();
        VariableOutputDTO liquidityVar = new VariableOutputDTO();
        liquidityVar.setAccountsReceivableTurnoverRatio("47.00");
        liquidityVar.setAccountsReceivableTurnoverScore("4.00");
        liquidityVar.setCurrentAssetsByCurrentLiabilitiesRatio("1.27");
        liquidityVar.setCurrentAssetsByCurrentLiabilitiesScore("1.75");
        liquidityVar.setNetCashByTotalAssetsRatio("-51.75");
        liquidityVar.setNetCashByTotalAssetsScore("1.00");
        liquidityVar.setUnderbillingsByWorkingCapitalRatio("0.00");
        liquidityVar.setUnderbillingsByWorkingCapitalScore("8.00");
        liquidityVariables.add(liquidityVar);
        liquidityCategory.setVariableOutput(liquidityVariables);
        categoryOutputs.add(liquidityCategory);

        // Leverage Category
        CategoryOutputDTO leverageCategory = new CategoryOutputDTO();
        leverageCategory.setCategoryName("Leverage");
        leverageCategory.setCategoryScore("20.00");
        leverageCategory.setCategoryScoreColor("yellow");

        List<VariableOutputDTO> leverageVariables = new ArrayList<>();
        VariableOutputDTO leverageVar = new VariableOutputDTO();
        leverageVar.setCurrentBankDebtByTotalAssetsRatio("0.00");
        leverageVar.setCurrentBankDebtByTotalAssetsScore("8.00");
        leverageVar.setTotalBankDebtByTotalAssetsRatio("0.00");
        leverageVar.setTotalBankDebtByTotalAssetsScore("8.00");
        leverageVar.setTotalLiabilitiesByEquityRatio("1.81");
        leverageVar.setTotalLiabilitiesByEquityScore("3.00");
        leverageVar.setWorkingCapitalByCurrentAssetsRatio("0.21");
        leverageVar.setWorkingCapitalByCurrentAssetsScore("1.00");
        leverageVariables.add(leverageVar);
        leverageCategory.setVariableOutput(leverageVariables);
        categoryOutputs.add(leverageCategory);

        // Operational Category
        CategoryOutputDTO operationalCategory = new CategoryOutputDTO();
        operationalCategory.setCategoryName("Operational");
        operationalCategory.setCategoryScore("16.00");
        operationalCategory.setCategoryScoreColor("light red");

        List<VariableOutputDTO> operationalVariables = new ArrayList<>();
        VariableOutputDTO operationalVar = new VariableOutputDTO();
        operationalVar.setGrossProfitMarginRatio("8.25");
        operationalVar.setGrossProfitMarginScore("6.00");
        operationalVar.setNetProfitMarginRatio("2.13");
        operationalVar.setNetProfitMarginScore("2.00");
        operationalVar.setNetWorthByWorkProgramRatio("8.47");
        operationalVar.setNetWorthByWorkProgramScore("5.00");
        operationalVar.setOverheadByOperatingIncomeRatio("295.27");
        operationalVar.setOverheadByOperatingIncomeScore("0.00");
        operationalVar.setWorkingCapitalByWorkProgramRatio("4.51");
        operationalVar.setWorkingCapitalByWorkProgramScore("3.00");
        operationalVariables.add(operationalVar);
        operationalCategory.setVariableOutput(operationalVariables);
        categoryOutputs.add(operationalCategory);

        scoringOutput.setCategoryOutput(categoryOutputs);
        scoringStmt.setScoringOutput(scoringOutput);
        scoringStatements.add(scoringStmt);

        bamsScoring.setStatements(scoringStatements);
        response.setBamsScoringOutput(bamsScoring);

        return response;
    }
}
