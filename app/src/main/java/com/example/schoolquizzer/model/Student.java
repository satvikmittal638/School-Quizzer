package com.example.schoolquizzer.model;

public class Student {
    private long rollNo;
    private int schoolClass;
    private char schoolSection;
    private String name, password;

    public Student(long rollNo, int schoolClass, String name, String password) {
        this.rollNo = rollNo;
        this.schoolClass = schoolClass;
        this.name = name;
        this.password = password;
    }

    public Student() {
    }

    public long getRollNo() {
        return rollNo;
    }

    public void setRollNo(long rollNo) {
        this.rollNo = rollNo;
    }

    public int getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(int schoolClass) {
        this.schoolClass = schoolClass;
    }

    public char getSchoolSection() {
        return schoolSection;
    }

    public void setSchoolSection(char schoolSection) {
        this.schoolSection = schoolSection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
