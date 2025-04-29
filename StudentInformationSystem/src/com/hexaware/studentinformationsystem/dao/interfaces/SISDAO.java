package com.hexaware.studentinformationsystem.dao.interfaces;



import com.hexaware.studentinformationsystem.entity.Student;
import com.hexaware.studentinformationsystem.entity.Course;
import com.hexaware.studentinformationsystem.entity.Enrollment;
import com.hexaware.studentinformationsystem.entity.Payment;

import java.time.LocalDate;
import java.util.List;

public interface SISDAO {
    void enrollStudentInCourse(Student student, Course course);
    void assignTeacherToCourse(Course course, int teacherId);
    void recordPayment(Student student, double amount, LocalDate paymentDate);
    List<Enrollment> generateEnrollmentReport(Course course);
    List<Payment> generatePaymentReport(Student student);
    void calculateCourseStatistics(Course course);
}
