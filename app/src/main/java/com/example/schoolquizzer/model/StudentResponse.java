package com.example.schoolquizzer.model;

// For submission as well as question-wise analysis of the quiz
public class StudentResponse {
    private long id;
    private int schoolClass;
    private long questionId, quizId; // same q may have multiple correct answers
    private long studentRoll; // for identification of individual student
    private char optionSelected;

    public StudentResponse(int schoolClass, long questionId, long quizId, long studentRoll, char optionSelected) {
        this.schoolClass = schoolClass;
        this.questionId = questionId;
        this.quizId = quizId;
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
