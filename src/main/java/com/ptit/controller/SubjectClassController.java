package com.ptit.controller;

import com.ptit.DTO.request.InputFactorRequest;
import com.ptit.DTO.response.ListClassResponse;
import com.ptit.DTO.response.SubjectClassInfo;
import com.ptit.model.Subject;
import com.ptit.model.SubjectClass;
import com.ptit.model.Teacher;
import com.ptit.repository.StudentRepository;
import com.ptit.repository.SubjectClassRepository;
import com.ptit.repository.SubjectRepository;
import com.ptit.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SubjectClassController {
    @Autowired
    private SubjectClassRepository subjectClassRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/listClass/{id}")
    public ListClassResponse listClassResponse(@PathVariable Long id){
        ListClassResponse listClassResponse = new ListClassResponse();
        Optional<Teacher> teacher = teacherRepository.findById(id);
        Teacher teacher1 = teacher.get();

        List<SubjectClassInfo> subjectClassInfos = new ArrayList<SubjectClassInfo>();
        List<SubjectClass> subjectClasses = subjectClassRepository.findAllByTeacherID(teacher1.getId());
        for(SubjectClass s: subjectClasses){
            Long numberStudent = studentRepository.getNumberStudent(s.getId());
            Subject subject = subjectRepository.findAllBySubjectId(s.getSubject_id());

            SubjectClassInfo subjectClassInfo = new SubjectClassInfo();
            subjectClassInfo.setSubject(subject);
            subjectClassInfo.setNumberStudent(numberStudent);
            subjectClassInfo.setSubjectClass(s);
            subjectClassInfos.add(subjectClassInfo);
        }
        listClassResponse.setSubjectClassInfos(subjectClassInfos);
        return listClassResponse;
    }

    @PutMapping("/thangdiem/{id}")
    public String thangdiem (@PathVariable Long id, @RequestBody InputFactorRequest inputFactorRequest){
        Optional<SubjectClass> subjectClass = subjectClassRepository.findById(id);
        SubjectClass subjectClass1 = subjectClass.get();
        subjectClass1.setFactor_assignment(inputFactorRequest.getAssignmentFactor());
        subjectClass1.setFactor_exam(inputFactorRequest.getExamFactor());
        subjectClass1.setFactor_test(inputFactorRequest.getTestFactor());
        subjectClass1.setFactor_attendance(inputFactorRequest.getAttendanceFactor());
        subjectClassRepository.save(subjectClass1);
        return "1";
    }

    @GetMapping("/listClassByCourse/{courseId}/{teacherId}")
    public ListClassResponse listClassResponse(@PathVariable Long courseId, @PathVariable Long teacherId){
        ListClassResponse listClassResponse =  new ListClassResponse();
        List<SubjectClassInfo> subjectClassInfos = new ArrayList<SubjectClassInfo>();
        List<SubjectClass> subjectClasses = subjectClassRepository.findAllByTeacherIdAndCourseId(teacherId, courseId);
        for(SubjectClass s: subjectClasses){
            Long numberStudent = studentRepository.getNumberStudent(s.getId());
            Subject subject = subjectRepository.findAllBySubjectId(s.getSubject_id());

            SubjectClassInfo subjectClassInfo = new SubjectClassInfo();
            subjectClassInfo.setSubject(subject);
            subjectClassInfo.setNumberStudent(numberStudent);
            subjectClassInfo.setSubjectClass(s);
            subjectClassInfos.add(subjectClassInfo);
        }
        listClassResponse.setSubjectClassInfos(subjectClassInfos);
        return listClassResponse;
    }
}
