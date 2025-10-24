package com.restTemp.InterService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionInputDTO {
    private String bamsId;
    private String creditLensId;
    private String statementDate;
}