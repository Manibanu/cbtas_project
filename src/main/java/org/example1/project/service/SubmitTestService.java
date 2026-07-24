package org.example1.project.service;


import org.example1.project.dto.SubmitAnswerDTO;
import org.example1.project.entity.*;
import org.example1.project.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubmitTestService {


    @Autowired
    private TestRepository testRepository;


    @Autowired
    private QuestionRepo questionRepository;


    @Autowired
    private AnswerRepository answerRepository;


    @Autowired
    private ResultRepository resultRepository;




    public Result submitTest(Long testId,
                             List<SubmitAnswerDTO> answers){



        Test test = testRepository.findById(testId)

                .orElseThrow(
                        () -> new RuntimeException(
                                "Test Not Found"
                        )
                );



        if(answers == null || answers.isEmpty()){

            throw new RuntimeException(
                    "No Answers Submitted"
            );

        }




        int correct = 0;




        for(SubmitAnswerDTO answerDTO : answers){



            Question question =
                    questionRepository.findById(
                                    answerDTO.getQuestionId()
                            )
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Question Not Found"
                                    )
                            );





            Answer answer = new Answer();



            answer.setTest(test);



            answer.setQuestion(question);



            answer.setSelectedAnswer(
                    answerDTO.getSelectedAnswer()
            );



            answerRepository.save(answer);






            if(question.getCorrectAnswer()
                    .equalsIgnoreCase(
                            answerDTO.getSelectedAnswer()
                    )){


                correct++;

            }


        }





        int totalQuestions = answers.size();



        int wrong =
                totalQuestions - correct;




        double percentage =
                ((double) correct / totalQuestions) * 100;






        Result result = new Result();



        // link test

        result.setTest(test);



        // link student/company/technology

        result.setStudent(
                test.getStudent()
        );


        result.setCompany(
                test.getCompany()
        );


        result.setTechnology(
                test.getTechnology()
        );





        result.setTotalQuestions(
                totalQuestions
        );



        result.setCorrectAnswers(
                correct
        );



        result.setWrongAnswers(
                wrong
        );



        result.setPercentage(
                percentage
        );






        if(percentage >= 50){

            result.setResultStatus(
                    "PASS"
            );

        }
        else{

            result.setResultStatus(
                    "FAIL"
            );

        }






        return resultRepository.save(result);


    }


}