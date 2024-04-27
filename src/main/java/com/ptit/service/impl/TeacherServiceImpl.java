package com.ptit.service.impl;

import com.ptit.DTO.response.LoginResponse;
import com.ptit.model.Course;
import com.ptit.model.Teacher;
import com.ptit.model.TeacherCourse;
import com.ptit.repository.CourseRepository;
import com.ptit.repository.TeacherCourseRepository;
import com.ptit.repository.TeacherRepository;
import com.ptit.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public LoginResponse teacherLogin(String username, String password) {
        LoginResponse loginResponse = new LoginResponse();
        System.out.println(username);
        Teacher teacher = teacherRepository.login(username, password);
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
