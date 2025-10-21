package com.restTemp.InterService.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOutputDTO {

    @JsonProperty("categoryName")
    private String categoryName;

    @JsonProperty("categoryScore")
    private String categoryScore;

    @JsonProperty("categoryScoreColor")
    private String categoryScoreColor;

    @JsonProperty("variableOutput")
    private List<VariableOutputDTO> variableOutput;
}
