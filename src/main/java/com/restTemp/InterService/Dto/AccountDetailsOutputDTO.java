package com.restTemp.InterService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsOutputDTO {

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountType")
    private String accountType;

    @JsonProperty("authorityLimitAggregateMaximum")
    private String authorityLimitAggregateMaximum;

    @JsonProperty("authorityLimitSingleMaximum")
    private String authorityLimitSingleMaximum;

    @JsonProperty("bamsId")
    private Integer bamsId;

    @JsonProperty("brokerAgencyName")
    private String brokerAgencyName;

    @JsonProperty("businessType")
    private String businessType;

    @JsonProperty("city")
    private String city;

    @JsonProperty("creditLensId")
    private Integer creditLensId;

    @JsonProperty("entityType")
    private String entityType;

    @JsonProperty("naicsNumber")
    private String naicsNumber;

    @JsonProperty("peOwned")
    private String peOwned;

    @JsonProperty("producerName")
    private String producerName;

    @JsonProperty("state")
    private String state;

    @JsonProperty("statementOutputs")
    private List<StatementOutputDTO> statementOutputs;

    @JsonProperty("streetAddress1")
    private String streetAddress1;

    @JsonProperty("streetAddress2")
    private String streetAddress2;

    @JsonProperty("tenureOfTheAccount")
    private String tenureOfTheAccount;

    @JsonProperty("tickerSymbol")
    private String tickerSymbol;

    @JsonProperty("underwriterName")
    private String underwriterName;

    @JsonProperty("zip")
    private String zip;
}
