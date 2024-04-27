package com.ptit.controller;

import com.ptit.DTO.request.LoginRequest;
import com.ptit.DTO.response.LoginResponse;
import com.ptit.model.Course;
import com.ptit.model.Teacher;
import com.ptit.model.TeacherCourse;
import com.ptit.repository.CourseRepository;
import com.ptit.repository.TeacherCourseRepository;
import com.ptit.repository.TeacherRepository;
import com.ptit.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherService teacherService;
    @GetMapping("/")
    private String welcome(){
        return "1";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();
        String username = loginRequest.getUsername();;
        String password = loginRequest.getPassword();
        loginResponse = teacherService.teacherLogin(username, password);
        return loginResponse;
    }
}
