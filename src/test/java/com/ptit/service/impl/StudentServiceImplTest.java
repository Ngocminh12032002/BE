package com.ptit.service.impl;

import com.ptit.DTO.response.StudentMark;
import com.ptit.DTO.response.StudentResponse;
import com.ptit.model.Mark;
import com.ptit.model.Student;
import com.ptit.model.SubjectClass;
import com.ptit.repository.MarkRepository;
import com.ptit.repository.StudentRepository;
import com.ptit.repository.SubjectClassRepository;
import com.ptit.service.StudentService;
import com.ptit.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StudentSerivceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectClassRepository subjectClassRepository;

    @Mock
    private MarkRepository markRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void StudentService_ListStudent_IDValid(){
        Long id = 1L;

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();

        // tạo mock cho subject class
        SubjectClass mockedSubjectClass = new SubjectClass();
        mockedSubjectClass.setId(id);
        mockedSubjectClass.setName("PTTK1");
        mockedSubjectClass.setSubject_id(1L);
        mockedSubjectClass.setTeacher_id(1L);
        mockedSubjectClass.setFactor_assignment(10.0);
        mockedSubjectClass.setFactor_attendance(10.0);
        mockedSubjectClass.setFactor_test(10.0);
        mockedSubjectClass.setFactor_exam(70.0);
        mockedSubjectClass.setCourse_id(1L);

        // tạo mock data cho list student
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Nguyen Ngoc Minh");
        student1.setEmail("minh@gmail.com");
        student1.setCode("B20DCCN437");
        student1.setSubject_class_id(1);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Nguyen Thi Nhung");
        student2.setEmail("nhung@gmail.com");
        student2.setCode("B20DCCN438");
        student2.setSubject_class_id(1);
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);


        // tạo mock cho điểm
        Mark markStudent1 = new Mark();
        markStudent1.setId(1L);
        markStudent1.setAttendance(9.0);
        markStudent1.setTest(6.5);
        markStudent1.setAssignment(-1.0);
        markStudent1.setExam(7.0);
        markStudent1.setGpa(6.35);
        markStudent1.setStudent_id(1);

        Mark markStudent2 = new Mark();
        markStudent1.setId(1L);
        markStudent1.setAttendance(9.0);
        markStudent1.setTest(6.5);
        markStudent1.setAssignment(-1.0);
        markStudent1.setExam(7.0);
        markStudent1.setGpa(6.35);
        markStudent1.setStudent_id(1);

        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(markStudent1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        StudentMark studentMark2 = new StudentMark();
        studentMark2.setStudent(student2);
        studentMark2.setMark(markStudent2);
        studentMark2.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark2);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("0");
        studentResponse.setSubjectClassID(id);

        // mock hành động của các hàm trong repository

        when(studentRepository.findAllBySubject_class_id(id)).thenReturn(studentList);
        when(markRepository.findAllByStudent_id(student1.getId())).thenReturn(markStudent1);
        when(markRepository.findAllByStudent_id(student2.getId())).thenReturn(markStudent2);
        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));

        // gọi hàm để nhận kết quả
        StudentResponse resStudentResponse = studentService.ListStudent(id);


        assertEquals(resStudentResponse,studentResponse);
    }

    @Test
    public void StudentService_ListStudent_IDNegative(){
        Long id = -1L;

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        SubjectClass mockedSubjectClass = new SubjectClass();
        List<Student> studentList = new ArrayList<>();

        // Stubbing the behavior of repository methods
        when(studentRepository.findAllBySubject_class_id(id)).thenReturn(studentList);
        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));

        // Call the method under test
        StudentResponse resStudentResponse = studentService.ListStudent(id);

        // Prepare the expected StudentResponse
        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("0");
        studentResponse.setSubjectClassID(id);

        // Assert that the actual and expected StudentResponse objects are equal
        assertEquals(resStudentResponse, studentResponse);
    }
}
