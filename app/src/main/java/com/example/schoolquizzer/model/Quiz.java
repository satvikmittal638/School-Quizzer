package com.example.schoolquizzer.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Quiz {
    private long id;

    private String subject;

    private String dateTimeFrom;
    private String dateTimeTo;

    private int durationInMin;

    // TODO persist arrays
    private String instructions;

    private int maxMarks;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Quiz(long id, String subject, String dateTimeFrom, String dateTimeTo, int durationInMin, String instructions, int maxMarks) {
        this.id = id;
        this.subject = subject;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.durationInMin = durationInMin;
        this.instructions = instructions;
        this.maxMarks = maxMarks;
    }

    public Quiz() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
