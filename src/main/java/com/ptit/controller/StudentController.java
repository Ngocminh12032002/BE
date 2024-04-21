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

    @GetMapping("/listStudent/{id}")
    public StudentResponse studentResponse(@PathVariable Long id){
        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = studentRepository.findAllBySubject_class_id(id);
        for (Student s: studentList){
            Mark mark = markRepository.findAllByStudent_id(s.getId());
            Optional<SubjectClass> subjectClass = subjectClassRepository.findById(id);
            SubjectClass subjectClass1 = subjectClass.get();
            StudentMark studentMark = new StudentMark();
            studentMark.setStudent(s);
            studentMark.setMark(mark);
            studentMark.setSubjectClass(subjectClass1);
            studentMarkList.add(studentMark);
        }
        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("0");
        studentResponse.setSubjectClassID(id);
        System.out.println(studentMarkList);
        return studentResponse;
    }

    @PutMapping("/listStudentByCondition/{id}")
    public StudentResponse response(@PathVariable Long id,@RequestBody ListStudentRequest listStudentRequest){
        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();
        Long condition = listStudentRequest.getCondition();
        String status = null;
        if (condition == 0){
            studentList = studentRepository.findAllBySubject_class_id(id);
            status = "1";
        } else if (condition == 1) {
            List<Mark> markList = markRepository.findAllMarkPass();
            for (Mark mark: markList){
                Student student1 = studentRepository.findStudentByIdAndSubject_class_id((long) mark.getStudent_id(), id);
                studentList.add(student1);
            }
            System.out.println(markList);
            status = "1";
        } else {
            List<Mark> markList = markRepository.findAllMarkFail();
            for (Mark mark: markList){
                Student student1 = studentRepository.findStudentByIdAndSubject_class_id((long) mark.getStudent_id(), id);
                studentList.add(student1);
            }
            System.out.println(markList);
            status="2";
        }

        for (Student s: studentList){
            Mark mark = markRepository.findAllByStudent_id(s.getId());
            Optional<SubjectClass> subjectClass = subjectClassRepository.findById(id);
            SubjectClass subjectClass1 = subjectClass.get();
            StudentMark studentMark = new StudentMark();
            studentMark.setStudent(s);
            studentMark.setMark(mark);
            studentMark.setSubjectClass(subjectClass1);
            studentMarkList.add(studentMark);
        }
        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter(status);
        studentResponse.setSubjectClassID(id);
        System.out.println(studentMarkList);
        return studentResponse;
    }

    @PutMapping("/listStudentByKey/{id}")
    public StudentResponse listStudentByKey(@PathVariable Long id,@RequestBody ListStudentRequest listStudentRequest){
        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();
        Long condition = listStudentRequest.getCondition();
        String keyword = listStudentRequest.getKeyword();
        String status = null;
        if (condition == 0){
            studentList = studentRepository.findAllBySubject_class_idAndKey(id, keyword);
            status = "1";
        } else if (condition == 1) {
            List<Mark> markList = markRepository.findAllMarkPass();
            for (Mark mark: markList){
                Optional<Student> student1 = studentRepository.findStudentByIdAndSubject_class_idAndKey((long) mark.getStudent_id(), id, keyword);
                Student student = new Student();
                if (!student1.isEmpty()){
                    student = student1.get();
                    System.out.println("S1" + student);
                }
                if (student.getId()!= null){
                    studentList.add(student);
                    System.out.println("listStu" + studentList);
                }
            }
            status = "1";
        } else {
            List<Mark> markList = markRepository.findAllMarkFail();
            for (Mark mark: markList){
                Optional<Student> student1 = studentRepository.findStudentByIdAndSubject_class_idAndKey((long) mark.getStudent_id(), id, keyword);
                Student student = new Student();
                if (!student1.isEmpty()){
                    student = student1.get();
                    System.out.println("S1" + student);
                }
                if (student.getId()!= null){
                    studentList.add(student);
                    System.out.println("listStu" + studentList);
                }
            }
            status="2";
        }

        for (Student s: studentList){
            Mark mark = markRepository.findAllByStudent_id(s.getId());
            Optional<SubjectClass> subjectClass = subjectClassRepository.findById(id);
            SubjectClass subjectClass1 = subjectClass.get();
            StudentMark studentMark = new StudentMark();
            studentMark.setStudent(s);
            studentMark.setMark(mark);
            studentMark.setSubjectClass(subjectClass1);
            studentMarkList.add(studentMark);
        }
        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter(status);
        studentResponse.setSubjectClassID(id);
        System.out.println(studentMarkList);
        return studentResponse;
    }
}
