package com.restTemp.InterService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoringStatementDTO {

    @JsonProperty("accountStatus")
    private String accountStatus;

    @JsonProperty("financialMetric")
    private FinancialMetricDTO financialMetric;

    @JsonProperty("preparedBy")
    private String preparedBy;

    @JsonProperty("scoringOutput")
    private ScoringOutputDTO scoringOutput;

    @JsonProperty("statementDate")
    private String statementDate;

    @JsonProperty("stmtType")
    private String stmtType;
}
