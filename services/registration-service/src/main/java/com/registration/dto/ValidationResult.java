package com.registration.dto;

public class ValidationResult {

    private final StudentDTO student;
    private final CourseDTO course;

    public ValidationResult(StudentDTO student, CourseDTO course) {
        this.student = student;
        this.course = course;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public CourseDTO getCourse() {
        return course;
    }
}
