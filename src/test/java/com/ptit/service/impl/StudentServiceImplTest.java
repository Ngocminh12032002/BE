package com.ptit.service.impl;

import com.ptit.DTO.request.ListStudentRequest;
import com.ptit.DTO.response.StudentMark;
import com.ptit.DTO.response.StudentResponse;
import com.ptit.model.Mark;
import com.ptit.model.Student;
import com.ptit.model.SubjectClass;
import com.ptit.repository.MarkRepository;
import com.ptit.repository.StudentRepository;
import com.ptit.repository.SubjectClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StudentServiceImplTest {

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
    
    @Test
    public void StudentService_ListStudent_IDNotExist(){
        Long id = 1000L;

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
        assertEquals(studentResponse.getStatusFilter(), resStudentResponse.getStatusFilter());
        assertEquals(studentResponse.getSubjectClassID(), resStudentResponse.getSubjectClassID());
        assertEquals(studentResponse.getStudentMarkList(), resStudentResponse.getStudentMarkList());
    }
    @Test
    public void StudentService_ListStudentByCondition_IDValid_ConditionAll(){
        long id = 1L;
        long condition = 0L;

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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
        studentList.add(student1);
        studentList.add(student2);

        List<Mark> markPassList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(5.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        markPassList.add(mark1);


        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("0");
        studentResponse.setSubjectClassID(id);

        // mock hành động của các hàm trong repository
        when(studentRepository.findAllBySubject_class_id(id)).thenReturn(studentList);
        when(markRepository.findAllMarkPass()).thenReturn(markPassList);
        when(studentRepository.findStudentByIdAndSubject_class_id((long) mark1.getStudent_id(),id)).thenReturn(student1);
        when(markRepository.findAllByStudent_id(student1.getId())).thenReturn(mark1);
        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));

        // Call the method under test
        StudentResponse resStudentResponse = studentService.ListStudentByCondition(id,condition);

        // Assert that the actual and expected StudentResponse objects are equal
        assertEquals(studentResponse.getStatusFilter(), resStudentResponse.getStatusFilter());
        assertEquals(studentResponse.getSubjectClassID(), resStudentResponse.getSubjectClassID());
        assertEquals(studentResponse.getStudentMarkList(), resStudentResponse.getStudentMarkList());
    }

    @Test
    public void StudentService_ListStudentByCondition_IDValid_ConditionPass(){
        long id = 1L;
        long condition = 1L;

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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
        studentList.add(student1);
        studentList.add(student2);

        List<Mark> markPassList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(5.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        markPassList.add(mark1);


        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("1");
        studentResponse.setSubjectClassID(id);

        // mock hành động của các hàm trong repository
        when(markRepository.findAllMarkPass()).thenReturn(markPassList);
        when(studentRepository.findStudentByIdAndSubject_class_id((long) mark1.getStudent_id(),id)).thenReturn(student1);
        when(markRepository.findAllByStudent_id(student1.getId())).thenReturn(mark1);
        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));

        // Call the method under test
        StudentResponse resStudentResponse = studentService.ListStudentByCondition(id,condition);

        // Assert that the actual and expected StudentResponse objects are equal
        assertEquals(studentResponse.getStatusFilter(), resStudentResponse.getStatusFilter());
        assertEquals(studentResponse.getSubjectClassID(), resStudentResponse.getSubjectClassID());
        assertEquals(studentResponse.getStudentMarkList(), resStudentResponse.getStudentMarkList());
    }

    @Test
    public void StudentService_ListStudentByCondition_IDValid_ConditionFail(){
        long id = 1L;
        long condition = 2L;

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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
        studentList.add(student1);

        List<Mark> markFailList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(0.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        markFailList.add(mark1);


        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("2");
        studentResponse.setSubjectClassID(id);

        // mock hành động của các hàm trong repository
        when(markRepository.findAllMarkFail()).thenReturn(markFailList);
        when(studentRepository.findStudentByIdAndSubject_class_id((long) mark1.getStudent_id(),id)).thenReturn(student1);
        when(markRepository.findAllByStudent_id(student1.getId())).thenReturn(mark1);
        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));

        // Call the method under test
        StudentResponse resStudentResponse = studentService.ListStudentByCondition(id,condition);

        // Assert that the actual and expected StudentResponse objects are equal
        assertEquals(studentResponse.getStatusFilter(), resStudentResponse.getStatusFilter());
        assertEquals(studentResponse.getSubjectClassID(), resStudentResponse.getSubjectClassID());
        assertEquals(studentResponse.getStudentMarkList(), resStudentResponse.getStudentMarkList());
    }

    @Test
    public void StudentService_ListStudentByCondition_IDValid_ConditionInvalid(){
        long id = 1L;
        long condition = 10L;

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

        // Call the method under test
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.ListStudentByCondition(id, condition);
        });

    }

    @Test
    public void StudentService_ListStudentByCondition_IDValid_ConditionInvalid2(){
        long id = 1L;
        long condition = -10L;

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

        // Call the method under test
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.ListStudentByCondition(id, condition);
        });

    }

    @Test
    public void testListStudentByKeyWithValidPartialKeywordCondition0() {
        // Given
        Long id = 1L;
        Long condition = 0L;
        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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


        ListStudentRequest listStudentRequest = new ListStudentRequest();
        listStudentRequest.setCondition(condition);


        // Mock danh sách sinh viên
        List<Student> mockStudentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Nguyễn Duy Tùng");
        student1.setEmail("tung@gmail.com");
        student1.setCode("B20DCCN625");
        student1.setSubject_class_id(1);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("NVA");
        student2.setEmail("nhung@gmail.com");
        student2.setCode("B20DCCN438");
        student2.setSubject_class_id(1);
        mockStudentList.add(student1);
        mockStudentList.add(student2);

        // Mock danh sách sinh viên không đạt điểm
        List<Mark> mockFailList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(0.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        mockFailList.add(mark1);

        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("0");
        studentResponse.setSubjectClassID(id);


        // Giả lập hành vi của phương thức findAllMarkFail() của repository
        when(markRepository.findAllMarkFail()).thenReturn(mockFailList);
        when(studentRepository.findAllBySubject_class_idAndKey(id, listStudentRequest.getKeyword()))
                .then(invocation -> {
                    String keyword = "n";
                    List<Student> result = new ArrayList<>();
                    for (Student student : mockStudentList) {
                        if (student != null && student.getName() != null && (student.getName().toLowerCase().contains(keyword.toLowerCase()) || student.getEmail().toLowerCase().contains(keyword.toLowerCase()) || student.getCode().toLowerCase().contains(keyword.toLowerCase()))) {
                            result.add(student);
                        }
                    }
                    return result;
                });

        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));

        // When
        StudentResponse studentResponse2 = studentService.ListStudentByKey(id, condition, listStudentRequest.getKeyword());

        // Then

        assertEquals(id, studentResponse2.getSubjectClassID());
        //assertTrue(!studentResponse2.getStudentMarkList().isEmpty());
    }
    @Test
    public void testListStudentByKeyWithValidPartialKeywordCondition1() {
        // Given
        Long id = 1L;
        Long condition = 1L;
        String keyword = "tùng";

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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



        // Mock danh sách sinh viên
        List<Student> mockStudentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Nguyễn Duy Tùng");
        student1.setEmail("tung@gmail.com");
        student1.setCode("B20DCCN625");
        student1.setSubject_class_id(1);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("NVA");
        student2.setEmail("nhung@gmail.com");
        student2.setCode("B20DCCN438");
        student2.setSubject_class_id(1);
        mockStudentList.add(student1);
        mockStudentList.add(student2);

        // Mock danh sách sinh viên không đạt điểm
        List<Mark> mockPassList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(4.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        mockPassList.add(mark1);

        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("1");
        studentResponse.setSubjectClassID(id);


        // Giả lập hành vi của phương thức findAllMarkFail() của repository
        when(markRepository.findAllByStudent_id(student1.getId())).thenReturn(mark1);
        when(markRepository.findAllMarkPass()).thenReturn(mockPassList);

        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));
        when(studentRepository.findStudentByIdAndSubject_class_idAndKey((long) mark1.getStudent_id(),id, keyword)).thenReturn(Optional.of(student1));

        // When
        StudentResponse studentResponse2 = studentService.ListStudentByKey(id, condition, keyword);

        // Then

        assertEquals(studentResponse.getStatusFilter(), studentResponse2.getStatusFilter());
        assertTrue(studentResponse2.getStudentMarkList().stream()
                .anyMatch(studentMark -> studentMark.getStudent().getName().toLowerCase().contains(keyword.toLowerCase())));
        //assertTrue(!studentResponse2.getStudentMarkList().isEmpty());
    }
    @Test
    public void testListStudentByKeyWithValidPartialKeywordCondition2() {
        // Given
        Long id = 1L;
        Long condition = 2L;
        String keyword = "t";

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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


        ListStudentRequest listStudentRequest = new ListStudentRequest();
        listStudentRequest.setCondition(condition);
        listStudentRequest.setKeyword(keyword);


        // Mock danh sách sinh viên
        List<Student> mockStudentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Nguyễn Duy Tùng");
        student1.setEmail("tung@gmail.com");
        student1.setCode("B20DCCN625");
        student1.setSubject_class_id(1);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("NVA");
        student2.setEmail("nhung@gmail.com");
        student2.setCode("B20DCCN438");
        student2.setSubject_class_id(1);
        mockStudentList.add(student1);
        mockStudentList.add(student2);

        // Mock danh sách sinh viên không đạt điểm
        List<Mark> mockFailList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(0.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        mockFailList.add(mark1);

        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("0");
        studentResponse.setSubjectClassID(id);


        // Giả lập hành vi của phương thức findAllMarkFail() của repository
        when(markRepository.findAllByStudent_id(student1.getId())).thenReturn(mark1);
        when(markRepository.findAllMarkFail()).thenReturn(mockFailList);



        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));
        when(studentRepository.findStudentByIdAndSubject_class_idAndKey((long) mark1.getStudent_id(),id, keyword)).thenReturn(Optional.of(student1));

        // When
        StudentResponse studentResponse2 = studentService.ListStudentByKey(id, condition, keyword);

        // Then
        assertTrue(studentResponse2.getStudentMarkList().stream()
                .anyMatch(studentMark -> studentMark.getStudent().getName().toLowerCase().contains(keyword.toLowerCase())));
        //assertEquals(id, studentResponse2.getSubjectClassID());
        //assertTrue(!studentResponse2.getStudentMarkList().isEmpty());
    }

    @Test
    public void testListStudentByKeyWithInvalidPartialKeywordCondition0() {
        // Given
        Long id = 1L;
        Long condition = 0L;
        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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


        ListStudentRequest listStudentRequest = new ListStudentRequest();
        listStudentRequest.setCondition(condition);


        // Mock danh sách sinh viên
        List<Student> mockStudentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Nguyễn Duy Tùng");
        student1.setEmail("tung@gmail.com");
        student1.setCode("B20DCCN625");
        student1.setSubject_class_id(1);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("NVA");
        student2.setEmail("nhung@gmail.com");
        student2.setCode("B20DCCN438");
        student2.setSubject_class_id(1);
        mockStudentList.add(student1);
        mockStudentList.add(student2);

        // Mock danh sách sinh viên không đạt điểm
        List<Mark> mockFailList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(0.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        mockFailList.add(mark1);

        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("0");
        studentResponse.setSubjectClassID(id);


        // Giả lập hành vi của phương thức findAllMarkFail() của repository
        when(markRepository.findAllMarkFail()).thenReturn(mockFailList);
        when(studentRepository.findAllBySubject_class_idAndKey(id, listStudentRequest.getKeyword()))
                .then(invocation -> {
                    String keyword = "q";
                    List<Student> result = new ArrayList<>();
                    for (Student student : mockStudentList) {
                        if (student != null && student.getName() != null && (student.getName().toLowerCase().contains(keyword.toLowerCase()) || student.getEmail().toLowerCase().contains(keyword.toLowerCase()) || student.getCode().toLowerCase().contains(keyword.toLowerCase()))) {
                            result.add(student);
                        }
                    }
                    return result;
                });

        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));

        // When
        StudentResponse studentResponse2 = studentService.ListStudentByKey(id, condition, listStudentRequest.getKeyword());

        // Then

        assertEquals(id, studentResponse2.getSubjectClassID());
        assertFalse(!studentResponse2.getStudentMarkList().isEmpty());
    }
    @Test
    public void testListStudentByKeyWithInvalidPartialKeywordCondition1() {
        // Given
        Long id = 1L;
        Long condition = 1L;
        String keyword = "t";

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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


        ListStudentRequest listStudentRequest = new ListStudentRequest();
        listStudentRequest.setCondition(condition);
        listStudentRequest.setKeyword(keyword);


        // Mock danh sách sinh viên
        List<Student> mockStudentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Nguyễn Duy Tùng");
        student1.setEmail("tung@gmail.com");
        student1.setCode("B20DCCN625");
        student1.setSubject_class_id(1);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("NVA");
        student2.setEmail("nhung@gmail.com");
        student2.setCode("B20DCCN438");
        student2.setSubject_class_id(1);
        mockStudentList.add(student1);
        mockStudentList.add(student2);

        // Mock danh sách sinh viên không đạt điểm
        List<Mark> mockPassList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(4.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        mockPassList.add(mark1);

        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("1");
        studentResponse.setSubjectClassID(id);


        // Giả lập hành vi của phương thức findAllMarkFail() của repository
        when(markRepository.findAllByStudent_id(student1.getId())).thenReturn(mark1);
        when(markRepository.findAllMarkFail()).thenReturn(mockPassList);



        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));
        when(studentRepository.findStudentByIdAndSubject_class_idAndKey((long) mark1.getStudent_id(),id, keyword)).thenReturn(Optional.of(student1));

        // When
        StudentResponse studentResponse2 = studentService.ListStudentByKey(id, condition, keyword);

        // Then
        assertFalse(studentResponse2.getStudentMarkList().stream()
                .anyMatch(studentMark -> studentMark.getStudent().getName().toLowerCase().contains(keyword.toLowerCase())));
        //assertEquals(id, studentResponse2.getSubjectClassID());
        //assertTrue(!studentResponse2.getStudentMarkList().isEmpty());
    }
    @Test
    public void testListStudentByKeyWithInvalidPartialKeywordCondition2() {
        // Given
        Long id = 1L;
        Long condition = 2L;
        String keyword = "^";

        StudentResponse studentResponse = new StudentResponse();
        List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
        List<Student> studentList = new ArrayList<Student>();

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


        ListStudentRequest listStudentRequest = new ListStudentRequest();
        listStudentRequest.setCondition(condition);
        listStudentRequest.setKeyword(keyword);


        // Mock danh sách sinh viên
        List<Student> mockStudentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Nguyễn Duy Tùng");
        student1.setEmail("tung@gmail.com");
        student1.setCode("B20DCCN625");
        student1.setSubject_class_id(1);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("NVA");
        student2.setEmail("nhung@gmail.com");
        student2.setCode("B20DCCN438");
        student2.setSubject_class_id(1);
        mockStudentList.add(student1);
        mockStudentList.add(student2);

        // Mock danh sách sinh viên không đạt điểm
        List<Mark> mockFailList = new ArrayList<>();
        // tạo mock cho điểm
        Mark mark1 = new Mark();
        mark1.setId(1L);
        mark1.setAttendance(9.0);
        mark1.setTest(6.5);
        mark1.setAssignment(0.0);
        mark1.setExam(7.0);
        mark1.setGpa(6.95);
        mark1.setStudent_id(1);
        mockFailList.add(mark1);

        // mock StudentMark
        StudentMark studentMark1 = new StudentMark();
        studentMark1.setStudent(student1);
        studentMark1.setMark(mark1);
        studentMark1.setSubjectClass(mockedSubjectClass);
        studentMarkList.add(studentMark1);

        studentResponse.setStudentMarkList(studentMarkList);
        studentResponse.setStatusFilter("0");
        studentResponse.setSubjectClassID(id);


        // Giả lập hành vi của phương thức findAllMarkFail() của repository
        when(markRepository.findAllByStudent_id(student1.getId())).thenReturn(mark1);
        when(markRepository.findAllMarkFail()).thenReturn(mockFailList);



        when(subjectClassRepository.findById(id)).thenReturn(Optional.of(mockedSubjectClass));
        when(studentRepository.findStudentByIdAndSubject_class_idAndKey((long) mark1.getStudent_id(),id, keyword)).thenReturn(Optional.of(student1));

        // When
        StudentResponse studentResponse2 = studentService.ListStudentByKey(id, condition, keyword);

        // Then
        assertFalse(studentResponse2.getStudentMarkList().stream()
                .anyMatch(studentMark -> studentMark.getStudent().getName().toLowerCase().contains(keyword.toLowerCase())));
        //assertEquals(id, studentResponse2.getSubjectClassID());
        //assertTrue(!studentResponse2.getStudentMarkList().isEmpty());
    }
}
