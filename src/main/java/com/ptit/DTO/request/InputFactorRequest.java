package com.ptit.DTO.request;

import lombok.Data;

@Data
public class InputFactorRequest {
    private Double assignmentFactor;
    private Double attendanceFactor;
    private Double testFactor;
    private Double examFactor;
}
