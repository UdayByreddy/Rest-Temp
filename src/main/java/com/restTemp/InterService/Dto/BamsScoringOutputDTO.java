package com.restTemp.InterService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BamsScoringOutputDTO {

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("accountType")
    private String accountType;

    @JsonProperty("bamsId")
    private String bamsId;

    @JsonProperty("branchName")
    private String branchName;

    @JsonProperty("businessType")
    private String businessType;

    @JsonProperty("scoreStatus")
    private String scoreStatus;

    @JsonProperty("statements")
    private List<ScoringStatementDTO> statements;
}
