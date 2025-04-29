package com.hexaware.studentinformationsystem.dao.interfaces;


import com.hexaware.studentinformationsystem.entity.Enrollment;
import com.hexaware.studentinformationsystem.exceptions.StudentNotFoundException;

import java.util.List;

public interface EnrollmentDAO {
    void addEnrollment(Enrollment enrollment);
    void updateEnrollment(Enrollment enrollment) throws StudentNotFoundException;
    void deleteEnrollment(int enrollmentId);
    Enrollment getEnrollmentById(int enrollmentId);
    List<Enrollment> getAllEnrollments();
	List<Enrollment> getEnrollmentsByStudentId(int studentId);
	List<Enrollment> getEnrollmentsByCourseId(int courseId);
}
