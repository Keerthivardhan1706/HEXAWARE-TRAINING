package com.hexaware.studentinformationsystem.entity;


import java.time.LocalDate;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private LocalDate enrollmentDate;

    // Constructors
    public Enrollment() {
    }

    public Enrollment(int enrollmentId, int studentId, int courseId, LocalDate enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    public Enrollment(int studentId, int courseId, LocalDate enrollmentDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters & Setters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    // toString() for easy printing
    @Override
    public String toString() {
        return "Enrollment{" +
               "enrollmentId=" + enrollmentId +
               ", studentId=" + studentId +
               ", courseId=" + courseId +
               ", enrollmentDate=" + enrollmentDate +
               '}';
    }
}
