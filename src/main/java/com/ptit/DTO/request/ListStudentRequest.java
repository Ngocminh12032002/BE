package com.ptit.DTO.request;

import lombok.Data;

@Data
public class ListStudentRequest {
    private Long condition;
    private String keyword;
}
