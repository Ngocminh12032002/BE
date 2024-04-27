package com.ptit.controller;

import com.ptit.DTO.request.InputScoreRequest;
import com.ptit.model.Mark;
import com.ptit.model.Student;
import com.ptit.model.Subject;
import com.ptit.model.SubjectClass;
import com.ptit.repository.MarkRepository;
import com.ptit.repository.StudentRepository;
import com.ptit.repository.SubjectClassRepository;
import com.ptit.service.MarkService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.io.InputStream;
@RestController
public class MarkController { 
    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectClassRepository subjectClassRepository;

    @Autowired
    private MarkService markService;

    @PutMapping("/nhapdiem/{id}")
    public String nhapdiem(@PathVariable Long id, @RequestBody InputScoreRequest inputScoreRequest) {
        Mark mark = markRepository.findAllByStudent_id(id);
        Optional<Student> student = studentRepository.findById(id);
        Student student1 = student.get();
        double score = (inputScoreRequest.getAssignment() * 10 + inputScoreRequest.getAttendance() * 10 + inputScoreRequest.getExam() * 70 + inputScoreRequest.getTest() * 10) / 100;
        boolean b = markService.InputScore(id, inputScoreRequest.getAttendance(), inputScoreRequest.getExam(), inputScoreRequest.getAssignment(), inputScoreRequest.getTest(), score);
        return "{\"results\": true}";
    }

    @PostMapping("/importMark")
    public String importMark(@RequestParam("file")MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream); // Đọc file Excel (.xlsx)

            Sheet sheet = workbook.getSheetAt(0); // Lấy ra sheet đầu tiên (index bắt đầu từ 0)
            System.out.println(sheet.getLastRowNum());
            // Duyệt qua các dòng trong sheet và lấy dữ liệu
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    // Đọc các giá trị từ A2 đến H2
                    Cell cellA = row.getCell(0); // Cột A
                    Cell cellB = row.getCell(1); // Cột B
                    Cell cellC = row.getCell(2); // Cột C
                    Cell cellD = row.getCell(3); // Cột D
                    Cell cellE = row.getCell(4); // Cột E
                    Cell cellF = row.getCell(5); // Cột F
                    Cell cellG = row.getCell(6); // Cột G
                    Cell cellH = row.getCell(7); // Cột H

                    // Kiểm tra xem các ô B2, C2, D2 có dạng chữ và E2, F2, G2, H2 có dạng số thực
                    if (cellB != null && cellB.getCellType() == CellType.STRING &&
                            cellC != null && cellC.getCellType() == CellType.STRING &&
                            cellD != null && cellD.getCellType() == CellType.STRING &&
                            cellE != null && cellE.getCellType() == CellType.NUMERIC &&
                            cellF != null && cellF.getCellType() == CellType.NUMERIC &&
                            cellG != null && cellG.getCellType() == CellType.NUMERIC &&
                            cellH != null && cellH.getCellType() == CellType.NUMERIC) {
                        // Lấy giá trị từ các ô
                        String cellBValue = cellB.getStringCellValue();
                        String cellCValue = cellC.getStringCellValue();
                        String cellDValue = cellD.getStringCellValue();
                        double cellEValue = cellE.getNumericCellValue();
                        double cellFValue = cellF.getNumericCellValue();
                        double cellGValue = cellG.getNumericCellValue();
                        double cellHValue = cellH.getNumericCellValue();

                        // Kiểm tra xem các giá trị số thực từ E2 đến H2 có nằm trong khoảng từ 0-10 không
                        if (cellEValue >= 0 && cellEValue <= 10 &&
                                cellFValue >= 0 && cellFValue <= 10 &&
                                cellGValue >= 0 && cellGValue <= 10 &&
                                cellHValue >= 0 && cellHValue <= 10) {
                            // Tiến hành nhập điểm vào hệ thống
                            System.out.println(cellBValue);
                            Student student = studentRepository.findByCode(cellBValue);
//
//                            Mark mark = markRepository.findAllByStudent_id(student.getId());
//                            mark.setAttendance(cellEValue);
//                            mark.setTest(cellFValue);
//                            mark.setAssignment(cellGValue);
//                            mark.setExam(cellHValue);
                            double tbScore = (cellEValue * 10 + cellFValue * 10 + cellGValue * 10 + cellHValue * 70)/100;
//                            mark.setGpa(tbScore);
//                            markRepository.save(mark);
                            boolean b = markService.InputScore(student.getId(), cellEValue, cellHValue, cellGValue, cellFValue,tbScore);
                        }
                    }
                }
            }
            workbook.close();
            inputStream.close();

            return "File processed successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to process file";
        }
    }
}
