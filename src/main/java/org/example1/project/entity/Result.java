package org.example1.project.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "results")
public class Result {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // Result belongs to Test
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;



    // Student who attended test
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;



    // Company assessment
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;



    // Technology assessment
    @ManyToOne
    @JoinColumn(name = "technology_id")
    private Technology technology;



    private Integer totalQuestions;


    private Integer correctAnswers;


    private Integer wrongAnswers;


    private Double percentage;


    private String resultStatus;




    // Default Constructor

    public Result() {

    }





    // Parameter Constructor

    public Result(Long id,
                  Test test,
                  Student student,
                  Company company,
                  Technology technology,
                  Integer totalQuestions,
                  Integer correctAnswers,
                  Integer wrongAnswers,
                  Double percentage,
                  String resultStatus) {

        this.id = id;
        this.test = test;
        this.student = student;
        this.company = company;
        this.technology = technology;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.percentage = percentage;
        this.resultStatus = resultStatus;

    }





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





    public Student getStudent() {
        return student;
    }


    public void setStudent(Student student) {
        this.student = student;
    }





    public Company getCompany() {
        return company;
    }


    public void setCompany(Company company) {
        this.company = company;
    }





    public Technology getTechnology() {
        return technology;
    }


    public void setTechnology(Technology technology) {
        this.technology = technology;
    }





    public Integer getTotalQuestions() {
        return totalQuestions;
    }


    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }





    public Integer getCorrectAnswers() {
        return correctAnswers;
    }


    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }





    public Integer getWrongAnswers() {
        return wrongAnswers;
    }


    public void setWrongAnswers(Integer wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }





    public Double getPercentage() {
        return percentage;
    }


    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }





    public String getResultStatus() {
        return resultStatus;
    }


    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

}