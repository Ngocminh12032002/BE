package com.ptit.DTO.response;

import com.ptit.model.Subject;
import com.ptit.model.SubjectClass;
import lombok.Data;

@Data
public class SubjectClassInfo {
    private SubjectClass subjectClass;
    private Long numberStudent;
    private Subject subject;
}
