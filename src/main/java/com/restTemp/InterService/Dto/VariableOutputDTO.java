package com.restTemp.InterService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariableOutputDTO {

    // Liquidity variables
    @JsonProperty("accountsReceivableTurnoverRatio")
    private String accountsReceivableTurnoverRatio;

    @JsonProperty("accountsReceivableTurnoverScore")
    private String accountsReceivableTurnoverScore;

    @JsonProperty("currentAssetsByCurrentLiabilitiesRatio")
    private String currentAssetsByCurrentLiabilitiesRatio;

    @JsonProperty("currentAssetsByCurrentLiabilitiesScore")
    private String currentAssetsByCurrentLiabilitiesScore;

    @JsonProperty("netCashByTotalAssetsRatio")
    private String netCashByTotalAssetsRatio;

    @JsonProperty("netCashByTotalAssetsScore")
    private String netCashByTotalAssetsScore;

    @JsonProperty("underbillingsByWorkingCapitalRatio")
    private String underbillingsByWorkingCapitalRatio;

    @JsonProperty("underbillingsByWorkingCapitalScore")
    private String underbillingsByWorkingCapitalScore;

    // Leverage variables
    @JsonProperty("currentBankDebtByTotalAssetsRatio")
    private String currentBankDebtByTotalAssetsRatio;

    @JsonProperty("currentBankDebtByTotalAssetsScore")
    private String currentBankDebtByTotalAssetsScore;

    @JsonProperty("totalBankDebtByTotalAssetsRatio")
    private String totalBankDebtByTotalAssetsRatio;

    @JsonProperty("totalBankDebtByTotalAssetsScore")
    private String totalBankDebtByTotalAssetsScore;

    @JsonProperty("totalLiabilitiesByEquityRatio")
    private String totalLiabilitiesByEquityRatio;

    @JsonProperty("totalLiabilitiesByEquityScore")
    private String totalLiabilitiesByEquityScore;

    @JsonProperty("workingCapitalByCurrentAssetsRatio")
    private String workingCapitalByCurrentAssetsRatio;

    @JsonProperty("workingCapitalByCurrentAssetsScore")
    private String workingCapitalByCurrentAssetsScore;

    // Operational variables
    @JsonProperty("grossProfitMarginRatio")
    private String grossProfitMarginRatio;

    @JsonProperty("grossProfitMarginScore")
    private String grossProfitMarginScore;

    @JsonProperty("netProfitMarginRatio")
    private String netProfitMarginRatio;

    @JsonProperty("netProfitMarginScore")
    private String netProfitMarginScore;

    @JsonProperty("netWorthByWorkProgramRatio")
    private String netWorthByWorkProgramRatio;

    @JsonProperty("netWorthByWorkProgramScore")
    private String netWorthByWorkProgramScore;

    @JsonProperty("overheadByOperatingIncomeRatio")
    private String overheadByOperatingIncomeRatio;

    @JsonProperty("overheadByOperatingIncomeScore")
    private String overheadByOperatingIncomeScore;

    @JsonProperty("workingCapitalByWorkProgramRatio")
    private String workingCapitalByWorkProgramRatio;

    @JsonProperty("workingCapitalByWorkProgramScore")
    private String workingCapitalByWorkProgramScore;
}
