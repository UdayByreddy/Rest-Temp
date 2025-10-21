package com.restTemp.InterService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoringOutputDTO {

    @JsonProperty("categoryOutput")
    private List<CategoryOutputDTO> categoryOutput;

    @JsonProperty("futureNetCashFlow")
    private String futureNetCashFlow;

    @JsonProperty("riskScale")
    private String riskScale;

    @JsonProperty("riskScaleColor")
    private String riskScaleColor;

    @JsonProperty("totalScore")
    private String totalScore;

    @JsonProperty("totalScoreColor")
    private String totalScoreColor;

    @JsonProperty("totalWorkProgram")
    private String totalWorkProgram;
}