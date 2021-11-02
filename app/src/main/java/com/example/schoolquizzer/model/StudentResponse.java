package com.example.schoolquizzer.model;

// For submission as well as question-wise analysis of the quiz
public class StudentResponse {
    private long id;
    private int schoolClass;
    private long questionId, quizId; // same q may have multiple correct answers
    private long studentRoll; // for identification of individual student
    private char optionSelected;
    private int timeTakenInSec;


    public StudentResponse(int schoolClass, long questionId, long quizId, long studentRoll, char optionSelected, int timeTakenInSec) {
        this.schoolClass = schoolClass;
        this.questionId = questionId;
        this.quizId = quizId;
        this.studentRoll = studentRoll;
        this.optionSelected = optionSelected;
        this.timeTakenInSec = timeTakenInSec;
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

    public int getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(int schoolClass) {
        this.schoolClass = schoolClass;
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    public int getTimeTakenInSec() {
        return timeTakenInSec;
    }

    public void setTimeTakenInSec(int timeTakenInSec) {
        this.timeTakenInSec = timeTakenInSec;
    }
}
