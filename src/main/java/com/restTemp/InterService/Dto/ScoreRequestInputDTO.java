package com.restTemp.InterService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRequestInputDTO {
    private String scoreRequestId;
    private String transactionTimeStamp;
    private String sourceSystem;
}