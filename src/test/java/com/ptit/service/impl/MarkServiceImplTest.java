package com.ptit.service.impl;

import com.ptit.DTO.request.InputScoreRequest;
import com.ptit.model.Mark;
import com.ptit.repository.MarkRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MarkServiceImplTest {

    @Mock
    private MarkRepository markRepository;

    @InjectMocks
    private MarkServiceImpl markService;

    @Test
    public void testInputScore() {
        // Given
        Long id = 1L;
        double attendance = 9.0;
        double exam = 8.5;
        double assignment = 7.0;
        double test = 8.0;
        double gpa = 8.0;

        Mark mockMark = new Mark();
        when(markRepository.findAllByStudent_id(id)).thenReturn(mockMark);
        when(markRepository.save(mockMark)).thenReturn(mockMark);

        markService.InputScore(id, attendance, exam, assignment, test, gpa);

        assertEquals(attendance, mockMark.getAttendance());
        assertEquals(exam, mockMark.getExam());
        assertEquals(assignment, mockMark.getAssignment());
        assertEquals(test, mockMark.getTest());
        assertEquals(gpa, mockMark.getGpa());
    }
    @Test
    public void testInvalidInputAssignmentScores() {
        // Given
        Long id = 1L;
        double invalidScore = 11.0; // Score above range

        Mark mockMark = new Mark();
        when(markRepository.findAllByStudent_id(id)).thenReturn(mockMark);

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            markService.InputScore(id, 8.0, 7.5, invalidScore, 8.0, 8.0);
        });
    }
    @Test
    public void testInvalidInputExamScores() {
        // Given
        Long id = 1L;
        double invalidScore = -1; // Score above range

        Mark mockMark = new Mark();
        when(markRepository.findAllByStudent_id(id)).thenReturn(mockMark);

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            markService.InputScore(id, 8.0, invalidScore, 10, 8.0, 8.0);
        });
    }
    @Test
    public void testInvalidInputAttendanceScores() {
        // Given
        Long id = 1L;
        double invalidScore = 11.0; // Score above range

        Mark mockMark = new Mark();
        when(markRepository.findAllByStudent_id(id)).thenReturn(mockMark);

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            markService.InputScore(id, invalidScore, 7.5, 10, 8.0, 8.0);
        });
    }
    @Test
    public void testInvalidInputTestScores() {
        // Given
        Long id = 1L;
        double invalidScore = 11.0; // Score above range

        Mark mockMark = new Mark();
        when(markRepository.findAllByStudent_id(id)).thenReturn(mockMark);

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> {
            markService.InputScore(id, 8.0, 7.5, 11, invalidScore, 8.0);
        });
    }
}
