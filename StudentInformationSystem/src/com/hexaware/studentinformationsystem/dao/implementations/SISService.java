package com.hexaware.studentinformationsystem.dao.implementations;

import com.hexaware.studentinformationsystem.dao.interfaces.SISDAO;
import com.hexaware.studentinformationsystem.entity.*;
import com.hexaware.studentinformationsystem.exception.*;

import java.sql.*;
import java.util.List;

public class SISService implements SISDAO {
    private Connection connection;

    public SISService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void enrollStudentInCourse(Student student, Course course) throws EnrollmentNotFoundException {
        EnrollmentService enrollmentService = new EnrollmentService(connection);
        // Assuming the Enrollment entity and service take care of creating a valid enrollment
        Enrollment enrollment = new Enrollment(student.getStudentId(), course.getCourseId(), LocalDate.now());
        enrollmentService.addEnrollment(enrollment);
    }

    @Override
    public void assignTeacherToCourse(Teacher teacher, Course course) throws TeacherNotFoundException {
        CourseService courseService = new CourseService(connection);
        // Assuming the Course entity and service handle assigning the teacher
        course.setTeacherId(teacher.getTeacherId());
        courseService.updateCourseInfo(course.getCourseId(), course.getCourseName(), teacher.getFirstName());
    }

    @Override
    public void recordPayment(Student student, double amount, Date paymentDate) throws InvalidInputException {
        PaymentService paymentService = new PaymentService(connection);
        Payment payment = new Payment(student.getStudentId(), amount, paymentDate.toLocalDate());
        paymentService.addPayment(payment);
    }

    @Override
    public List<Enrollment> generateEnrollmentReport(Course course) {
        EnrollmentService enrollmentService = new EnrollmentService(connection);
        return enrollmentService.getEnrollmentsByCourseId(course.getCourseId());
    }

    @Override
    public List<Payment> generatePaymentReport(Student student) {
        PaymentService paymentService = new PaymentService(connection);
        return paymentService.getPaymentsByStudentId(student.getStudentId());
    }

    @Override
    public void calculateCourseStatistics(Course course) {
        EnrollmentService enrollmentService = new EnrollmentService(connection);
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(course.getCourseId());

        // You can calculate the number of students enrolled
        int numberOfStudents = enrollments.size();

        // Calculate total payments for the course (if payments are linked to enrollments)
        PaymentService paymentService = new PaymentService(connection);
        double totalPayments = 0;
        for (Enrollment enrollment : enrollments) {
            List<Payment> payments = paymentService.getPaymentsByStudentId(enrollment.getStudentId());
            for (Payment payment : payments) {
                totalPayments += payment.getAmount();
            }
        }

        System.out.println("Course Statistics:");
        System.out.println("Number of Students Enrolled: " + numberOfStudents);
        System.out.println("Total Payments Received: $" + totalPayments);
    }

    private void closeResources(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            // Do NOT close connection here
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
