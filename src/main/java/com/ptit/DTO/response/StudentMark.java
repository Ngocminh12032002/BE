package com.ptit.DTO.response;

import com.ptit.model.Mark;
import com.ptit.model.Student;
import com.ptit.model.SubjectClass;
import lombok.Data;

@Data
public class StudentMark{
    Student student;
    Mark mark;
    SubjectClass subjectClass;
}
