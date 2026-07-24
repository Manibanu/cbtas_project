package org.example1.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "test_questions")
public class TestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public TestQuestion() {
    }

    public TestQuestion(Long id, Test test, Question question) {
        this.id = id;
        this.test = test;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public Test getTest() {
        return test;
    }

    public Question getQuestion() {
        return question;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}