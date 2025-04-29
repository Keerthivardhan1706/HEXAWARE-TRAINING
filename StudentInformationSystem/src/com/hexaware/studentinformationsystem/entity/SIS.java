package com.hexaware.studentinformationsystem.entity;

import java.util.ArrayList;
import java.util.List;

public class SIS {
    private List<Student> students;
    private List<Course> courses;
    private List<Enrollment> enrollments;
    private List<Teacher> teachers;
    private List<Payment> payments;

    // Constructor for SIS class
    public SIS() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    // Methods to add items to the lists
    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    // Optionally, you can also include methods to get the lists if needed
    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
