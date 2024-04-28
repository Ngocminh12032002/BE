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
        if (Assignment < 0 || Assignment > 10 || Exam < 0 || Exam > 10 || Attendance < 0 || Attendance > 10 ||
                Test < 0 || Test > 10 || Gpa < 0 || Gpa > 10) {
            throw new IllegalArgumentException("Giá trị nhập vào phải nằm trong khoảng từ 0 đến 10");
        }
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
