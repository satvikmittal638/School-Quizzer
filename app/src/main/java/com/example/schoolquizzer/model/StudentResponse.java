package com.example.schoolquizzer.model;

public class StudentResponse {
    private long id;

    private long questionId; // same q may have multiple correct answers
    private long studentRoll; // for identification of individual student
    private char optionSelected;


    public StudentResponse(long questionId, long studentRoll, char optionSelected) {
        this.questionId = questionId;
        this.studentRoll = studentRoll;
        this.optionSelected = optionSelected;
    }

    public StudentResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(long studentRoll) {
        this.studentRoll = studentRoll;
    }

    public char getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(char optionSelected) {
        this.optionSelected = optionSelected;
    }
}
