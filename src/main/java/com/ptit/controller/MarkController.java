package com.ptit.controller;

import com.ptit.DTO.request.InputScoreRequest;
import com.ptit.model.Mark;
import com.ptit.model.Student;
import com.ptit.model.Subject;
import com.ptit.model.SubjectClass;
import com.ptit.repository.MarkRepository;
import com.ptit.repository.StudentRepository;
import com.ptit.repository.SubjectClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MarkController {
    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectClassRepository subjectClassRepository;

    @PutMapping("/nhapdiem/{id}")
    public String nhapdiem(@PathVariable Long id, @RequestBody InputScoreRequest inputScoreRequest) {
        Mark mark = markRepository.findAllByStudent_id(id);
        Optional<Student> student = studentRepository.findById(id);
        Student student1 = student.get();
        Optional<SubjectClass> subjectClass = subjectClassRepository.findById(Long.valueOf(student1.getSubject_class_id()));
        SubjectClass subjectClass1 = subjectClass.get();

        if (mark != null){
        double score = (inputScoreRequest.getAssignment() * subjectClass1.getFactor_assignment() + inputScoreRequest.getAttendance() * subjectClass1.getFactor_attendance() + inputScoreRequest.getExam() * subjectClass1.getFactor_exam() + inputScoreRequest.getTest() * subjectClass1.getFactor_test()) / 100;
        mark.setAssignment(inputScoreRequest.getAssignment());
        mark.setExam(inputScoreRequest.getExam());
        mark.setAttendance(inputScoreRequest.getAttendance());
        mark.setTest(inputScoreRequest.getTest());
        mark.setGpa(score);
        markRepository.save(mark);
        System.out.println(score);
        }
        else {
            Mark mark1 = new Mark();
            double score = (inputScoreRequest.getAssignment() * subjectClass1.getFactor_assignment() + inputScoreRequest.getAttendance() * subjectClass1.getFactor_attendance() + inputScoreRequest.getExam() * subjectClass1.getFactor_exam() + inputScoreRequest.getTest() * subjectClass1.getFactor_test()) / 100;
            mark1.setAssignment(inputScoreRequest.getAssignment());
            mark1.setExam(inputScoreRequest.getExam());
            mark1.setAttendance(inputScoreRequest.getAttendance());
            mark1.setTest(inputScoreRequest.getTest());
            mark1.setGpa(score);
            mark1.setStudent_id(Math.toIntExact(id));
            markRepository.save(mark1);
        }
        return "{\"results\": true}";
    }
}
