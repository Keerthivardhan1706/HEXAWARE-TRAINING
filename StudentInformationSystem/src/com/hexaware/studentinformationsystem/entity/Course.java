package com.hexaware.studentinformationsystem.entity;

public class Course {
    private int courseId;
    private String courseName;
    private int credits;
    private int teacherId;

    // Constructors
    public Course() {
    }

    public Course(int courseId, String courseName, int credits, int teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.teacherId = teacherId;
    }

    public Course(String courseName, int credits, int teacherId) {
        this.courseName = courseName;
        this.credits = credits;
        this.teacherId = teacherId;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    // toString() for display/debug
    @Override
    public String toString() {
        return "Course{" +
               "courseId=" + courseId +
               ", courseName='" + courseName + '\'' +
               ", credits=" + credits +
               ", teacherId=" + teacherId +
               '}';
    }
}
