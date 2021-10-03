package com.example.schoolquizzer.model;

import java.util.List;
import java.util.Map;

public class QuizAnalysis {
    private float classMarksAvg;
    private List<Student> leaderBoard;
    private long[][] marksFrequencyTable;
    private long attemptRateInPercent; // % of students who attempted the quiz out of all
    private long accuracyInPercent;

    public QuizAnalysis(float classMarksAvg, List<Student> leaderBoard, long[][] marksFrequencyTable, long attemptRateInPercent, long accuracyInPercent) {
        this.classMarksAvg = classMarksAvg;
        this.leaderBoard = leaderBoard;
        this.marksFrequencyTable = marksFrequencyTable;
        this.attemptRateInPercent = attemptRateInPercent;
        this.accuracyInPercent = accuracyInPercent;
    }

    public float getClassMarksAvg() {
        return classMarksAvg;
    }

    public void setClassMarksAvg(float classMarksAvg) {
        this.classMarksAvg = classMarksAvg;
    }

    public List<Student> getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(List<Student> leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public long[][] getMarksFrequencyTable() {
        return marksFrequencyTable;
    }

    public void setMarksFrequencyTable(long[][] marksFrequencyTable) {
        this.marksFrequencyTable = marksFrequencyTable;
    }

    public long getAttemptRateInPercent() {
        return attemptRateInPercent;
    }

    public void setAttemptRateInPercent(long attemptRateInPercent) {
        this.attemptRateInPercent = attemptRateInPercent;
    }

    public long getAccuracyInPercent() {
        return accuracyInPercent;
    }

    public void setAccuracyInPercent(long accuracyInPercent) {
        this.accuracyInPercent = accuracyInPercent;
    }
}
