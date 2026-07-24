package org.example1.project.entity;


import jakarta.persistence.*;


@Entity
public class Answer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    private Test test;



    @ManyToOne
    private Question question;



    private String selectedAnswer;



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Test getTest() {
        return test;
    }


    public void setTest(Test test) {
        this.test = test;
    }


    public Question getQuestion() {
        return question;
    }


    public void setQuestion(Question question) {
        this.question = question;
    }


    public String getSelectedAnswer() {
        return selectedAnswer;
    }


    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

}