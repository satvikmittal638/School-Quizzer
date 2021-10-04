package com.example.schoolquizzer.model;

// For analysis of individual student
public class QuizzesStudent {

    private int sno;

    private long quizId, studentRoll; // multiple quiz-student relations might exist

    private String type;

    // -2 implies that quiz is unattempted and -1 implies that quiz is attempted but not checked
    private int marksObtained; // to be updated only after a successful attempt of the test

    public QuizzesStudent(long quizId, long studentRoll, String type, int marksObtained) {
        this.quizId = quizId;
        this.studentRoll = studentRoll;
        this.type = type;
        this.marksObtained = marksObtained;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    public long getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(long studentRoll) {
        this.studentRoll = studentRoll;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }
}
