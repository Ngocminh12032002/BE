package com.ptit.controller;

import com.ptit.DTO.request.LoginRequest;
import com.ptit.DTO.response.LoginResponse;
import com.ptit.model.Course;
import com.ptit.model.Teacher;
import com.ptit.model.TeacherCourse;
import com.ptit.repository.CourseRepository;
import com.ptit.repository.TeacherCourseRepository;
import com.ptit.repository.TeacherRepository;
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

    @GetMapping("/")
    private String welcome(){
        return "1";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();
        Teacher teacher = teacherRepository.login(loginRequest.getUsername(), loginRequest.getPassword());
        List<TeacherCourse> teacherCourse = teacherCourseRepository.findAllByTeacher_id(teacher.getId());
        List<Course> courseList = new ArrayList<Course>();
        for (TeacherCourse teacherCourse1: teacherCourse) {
            Optional<Course> course = courseRepository.findById(teacherCourse1.getCourse_id());
            Course course1 = course.get();
            courseList.add(course1);
            System.out.println(course1);
        }
        if (teacher != null){
            loginResponse.setResult("true");
            loginResponse.setUsername(teacher.getUsername());
            loginResponse.setName(teacher.getName());
            loginResponse.setId(teacher.getId());
            loginResponse.setDob(teacher.getDob());
            loginResponse.setEmail(teacher.getEmail());
            loginResponse.setCountry(teacher.getCountry());
            loginResponse.setPhone_number(teacher.getPhone_number());
            loginResponse.setCourse(courseList);
        }
        else {
            loginResponse.setResult("false");
        }
        return loginResponse;
    }
}
