package com.hexaware.studentinformationsystem.dao.implementations;

import com.hexaware.studentinformationsystem.dao.interfaces.EnrollmentDAO;
import com.hexaware.studentinformationsystem.entity.Enrollment;
import com.hexaware.studentinformationsystem.exceptions.StudentNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class EnrollmentService implements EnrollmentDAO {
    private Connection connection;

    public EnrollmentService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Enrollment getEnrollmentById(int enrollmentId) {
        Enrollment enrollment = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM enrollments WHERE enrollmentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, enrollmentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                enrollment = mapResultSetToEnrollment(rs);
            } else {
                throw new StudentNotFoundException("Enrollment with ID " + enrollmentId + " not found.");
            }
        } catch (SQLException | StudentNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return enrollment;
    }

    @Override
    public void addEnrollment(Enrollment enrollment) {
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO enrollments (studentId, courseId, enrollmentDate) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getCourseId());
            ps.setDate(3, Date.valueOf(enrollment.getEnrollmentDate()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void updateEnrollment(Enrollment enrollment) throws StudentNotFoundException {
        PreparedStatement ps = null;

        try {
            String query = "UPDATE enrollments SET studentId = ?, courseId = ?, enrollmentDate = ? WHERE enrollmentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getCourseId());
            ps.setDate(3, Date.valueOf(enrollment.getEnrollmentDate()));
            ps.setInt(4, enrollment.getEnrollmentId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentNotFoundException("Enrollment with ID " + enrollment.getEnrollmentId() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void deleteEnrollment(int enrollmentId) {
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM enrollments WHERE enrollmentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, enrollmentId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentNotFoundException("Enrollment with ID " + enrollmentId + " not found.");
            }
        } catch (SQLException | StudentNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM enrollments WHERE studentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                enrollments.add(mapResultSetToEnrollment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return enrollments;
    }

    @Override
    public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
        List<Enrollment> enrollments = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM enrollments WHERE courseId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, courseId);
            rs = ps.executeQuery();

            while (rs.next()) {
                enrollments.add(mapResultSetToEnrollment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return enrollments;
    }

    private Enrollment mapResultSetToEnrollment(ResultSet rs) throws SQLException {
        return new Enrollment(
            rs.getInt("enrollmentId"),
            rs.getInt("studentId"),
            rs.getInt("courseId"),
            rs.getDate("enrollmentDate").toLocalDate()
        );
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
