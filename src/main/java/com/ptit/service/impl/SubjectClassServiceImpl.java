package com.ptit.service.impl;

import com.ptit.DTO.response.ListClassResponse;
import com.ptit.DTO.response.SubjectClassInfo;
import com.ptit.model.Subject;
import com.ptit.model.SubjectClass;
import com.ptit.repository.StudentRepository;
import com.ptit.repository.SubjectClassRepository;
import com.ptit.repository.SubjectRepository;
import com.ptit.service.SubjectClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectClassServiceImpl implements SubjectClassService {
    @Autowired
    private SubjectClassRepository subjectClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public ListClassResponse listClassByCourse(Long courseId, Long teacherId) {
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
