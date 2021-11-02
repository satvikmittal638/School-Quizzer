package com.example.schoolquizzer.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Quiz {
    private long id;
    // Teachers assignment details
    private long assignedBy;
    private LocalDateTime assignedOn; // auto populated

    // Examination Details
    private String quizName;
    private String subject;

    private String dateTimeFrom;
    private String dateTimeTo;

    private int durationInMin;

    // TODO persist arrays
    private String instructions;

    private int maxMarks;

    public Quiz(long id, long assignedBy, LocalDateTime assignedOn, String quizName, String subject, String dateTimeFrom, String dateTimeTo, int durationInMin, String instructions, int maxMarks) {
        this.id = id;
        this.assignedBy = assignedBy;
        this.assignedOn = assignedOn;
        this.quizName = quizName;
        this.subject = subject;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.durationInMin = durationInMin;
        this.instructions = instructions;
        this.maxMarks = maxMarks;
    }

    @RequiresApi(api = Build.VERSION_CODES.O) // For use of LocalDateTime package

    public Quiz() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public LocalDateTime getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(LocalDateTime assignedOn) {
        this.assignedOn = assignedOn;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime getDateTimeFrom() {
        return LocalDateTime.parse(dateTimeFrom);
    }

    public void setDateTimeFrom(String dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime getDateTimeTo() {
        return LocalDateTime.parse(dateTimeTo);
    }

    public void setDateTimeTo(String dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public int getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(int durationInMin) {
        this.durationInMin = durationInMin;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }
}
