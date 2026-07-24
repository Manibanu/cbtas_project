package org.example1.project.service;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example1.project.entity.Company;
import org.example1.project.entity.Question;
import org.example1.project.entity.Technology;
import org.example1.project.enums.Difficulty;
import org.example1.project.repository.CompanyRepository;
import org.example1.project.repository.QuestionRepo;
import org.example1.project.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private QuestionRepo questionRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    // DataFormatter safely converts ANY cell type (text, number, date, formula)
    // into a plain String, exactly as Excel displays it. This avoids the old
    // deprecated setCellType() approach, which can behave unreliably.
    private final DataFormatter dataFormatter = new DataFormatter();

    // This method takes the uploaded Excel file, reads every row,
    // converts each row into a Question object, and saves all of them to MySQL.
    public String importQuestions(MultipartFile file) throws Exception {

        List<Question> questionList = new ArrayList<>();

        // Open the uploaded file as a stream and load it into a Workbook (the whole Excel file)
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);

        // We only use the first sheet (index 0)
        Sheet sheet = workbook.getSheetAt(0);

        int successCount = 0;
        int skippedCount = 0;

        // Loop through every row in the sheet
        for (Row row : sheet) {

            // Row 0 is the header row (companyName, technologyName, difficulty...)
            // so we skip it and start reading from row 1.
            if (row.getRowNum() == 0) {
                continue;
            }

            // Skip completely empty rows
            if (row.getCell(0) == null) {
                continue;
            }

            try {
                String companyName = getCellValue(row, 0);
                String technologyName = getCellValue(row, 1);
                String difficultyStr = getCellValue(row, 2);
                String questionText = getCellValue(row, 3);
                String optionA = getCellValue(row, 4);
                String optionB = getCellValue(row, 5);
                String optionC = getCellValue(row, 6);
                String optionD = getCellValue(row, 7);
                String correctAnswer = getCellValue(row, 8);

                // Find the Company by name. If it doesn't exist in DB, skip this row.
                Company company = companyRepository.findByCompanyName(companyName)
                        .orElse(null);

                // Find the Technology by name. If it doesn't exist in DB, skip this row.
                Technology technology = technologyRepository.findByTechnologyName(technologyName)
                        .orElse(null);

                if (company == null || technology == null) {
                    System.out.println("Skipping row " + row.getRowNum()
                            + " -> company or technology not found: "
                            + companyName + " / " + technologyName);
                    skippedCount++;
                    continue;
                }


                // Convert the difficulty text (e.g. "EASY") into the enum
                Difficulty difficulty = Difficulty.valueOf(difficultyStr.trim().toUpperCase());

                // Build the Question object
                Question question = new Question();
                question.setQuestion(questionText);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setOptionD(optionD);
                question.setCorrectAnswer(correctAnswer);
                question.setDifficulty(difficulty);
                question.setCompany(company);
                question.setTechnology(technology);

                questionList.add(question);
                successCount++;

            } catch (Exception e) {
                // If one row has bad data, don't crash the whole import — just skip that row
                System.out.println("Error on row " + row.getRowNum() + ": " + e.getMessage());
                skippedCount++;
            }
        }

        workbook.close();

        // Save all valid questions to MySQL in one batch call
        questionRepository.saveAll(questionList);

        return "Import finished. Saved: " + successCount + ", Skipped: " + skippedCount;
    }

    // Helper method: reads a cell as text, no matter what type it actually is
    // (text, number, etc.), and never throws an error for a null or blank cell.
    private String getCellValue(Row row, int cellIndex) {
        if (row.getCell(cellIndex) == null) {
            return "";
        }
        return dataFormatter.formatCellValue(row.getCell(cellIndex)).trim();
    }
}