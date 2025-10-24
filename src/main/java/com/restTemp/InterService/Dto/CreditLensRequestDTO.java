package com.restTemp.InterService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditLensRequestDTO {
    private ScoreRequestInputDTO scoreRequestInput;
    private SubmissionInputDTO SubmissionInput;
}