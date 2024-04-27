package com.ptit.controller;

import com.ptit.DTO.request.ListStudentRequest;
import com.ptit.DTO.response.StudentMark;
import com.ptit.DTO.response.StudentResponse;
import com.ptit.model.Mark;
import com.ptit.model.Student;
import com.ptit.model.SubjectClass;
import com.ptit.repository.MarkRepository;
import com.ptit.repository.StudentRepository;
import com.ptit.repository.SubjectClassRepository;
import com.ptit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private SubjectClassRepository subjectClassRepository;

    @Autowired
    private StudentService studentService;

    @GetMapping("/listStudent/{id}")
    public StudentResponse studentResponse(@PathVariable Long id){
        StudentResponse studentResponse = new StudentResponse();
        studentResponse = studentService.ListStudent(id);
        return studentResponse;
    }

    @PutMapping("/listStudentByCondition/{id}")
    public StudentResponse response(@PathVariable Long id,@RequestBody ListStudentRequest listStudentRequest){
        StudentResponse studentResponse = new StudentResponse();
        Long condition = listStudentRequest.getCondition();
        studentResponse = studentService.ListStudentByCondition(id, condition);
        return studentResponse;
    }

    @PutMapping("/listStudentByKey/{id}")
    public StudentResponse listStudentByKey(@PathVariable Long id,@RequestBody ListStudentRequest listStudentRequest){
        StudentResponse studentResponse = new StudentResponse();
        Long condition = listStudentRequest.getCondition();
        String keyword = listStudentRequest.getKeyword();
        studentResponse = studentService.ListStudentByKey(id, condition, keyword);
        return studentResponse;
    }
}
