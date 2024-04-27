package com.ptit.service.impl;

import com.ptit.model.Mark;
import com.ptit.repository.MarkRepository;
import com.ptit.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkServiceImpl implements MarkService {
    @Autowired
    private MarkRepository markRepository;
    @Override
    public boolean InputScore(Long id, double Attendance, double Exam, double Assignment, double Test, double Gpa) {
        Mark mark = markRepository.findAllByStudent_id(id);
        mark.setAssignment(Assignment);
        mark.setExam(Exam);
        mark.setAttendance(Attendance);
        mark.setTest(Test);
        mark.setGpa(Gpa);
        markRepository.save(mark);
        return false;
    }
}
