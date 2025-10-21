package com.restTemp.InterService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOutputDTO {

    @JsonProperty("cash")
    private String cash;

    @JsonProperty("cashColor")
    private String cashColor;

    @JsonProperty("netWorth")
    private String netWorth;

    @JsonProperty("netWorthColor")
    private String netWorthColor;

    @JsonProperty("revenueSize")
    private String revenueSize;

    @JsonProperty("statementDate")
    private String statementDate;
}
