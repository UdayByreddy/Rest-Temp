package com.restTemp.InterService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialMetricDTO {

    @JsonProperty("debtToNetWorth")
    private String debtToNetWorth;

    @JsonProperty("debtToNetWorthColor")
    private String debtToNetWorthColor;

    @JsonProperty("freeCashFlow")
    private String freeCashFlow;

    @JsonProperty("freeCashFlowColor")
    private String freeCashFlowColor;

    @JsonProperty("ibdToNetWorth")
    private String ibdToNetWorth;

    @JsonProperty("ibdToNetWorthColor")
    private String ibdToNetWorthColor;

    @JsonProperty("interestCoverage")
    private String interestCoverage;

    @JsonProperty("interestCoverageColor")
    private String interestCoverageColor;

    @JsonProperty("netProfitMargin")
    private String netProfitMargin;

    @JsonProperty("netProfitMarginColor")
    private String netProfitMarginColor;

    @JsonProperty("operatingMargin")
    private String operatingMargin;

    @JsonProperty("operatingMarginColor")
    private String operatingMarginColor;

    @JsonProperty("profitabilityRatio")
    private String profitabilityRatio;

    @JsonProperty("profitabilityRatioColor")
    private String profitabilityRatioColor;
}
