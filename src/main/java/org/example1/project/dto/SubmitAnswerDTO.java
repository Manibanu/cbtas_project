package org.example1.project.dto;


public class SubmitAnswerDTO {


    private Long questionId;


    private String selectedAnswer;



    public Long getQuestionId() {
        return questionId;
    }


    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }


    public String getSelectedAnswer() {
        return selectedAnswer;
    }


    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

}