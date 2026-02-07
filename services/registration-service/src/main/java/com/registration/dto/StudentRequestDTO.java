package com.registration.dto;

public class StudentRequestDTO {
    private int studentId;
    private String studentName;


    /**
     * @return int return the studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * @return String return the StudentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @param StudentName the StudentName to set
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}