package com.hexaware.studentinformationsystem.dao.implementations;

import com.hexaware.studentinformationsystem.dao.interfaces.StudentDAO;
import com.hexaware.studentinformationsystem.entity.Student;
import com.hexaware.studentinformationsystem.exception.StudentNotFoundException;
import com.hexaware.studentinformationsystem.exception.InvalidInputException;

import java.sql.*;

public class StudentService implements StudentDAO {
    private Connection connection;

    public StudentService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student getStudentById(int studentId) throws StudentNotFoundException {
        Student student = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM students WHERE studentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                student = mapResultSetToStudent(rs);
            } else {
                throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return student;
    }

    @Override
    public void addStudent(Student student) throws InvalidInputException {
        if (student == null || student.getFirstName().isEmpty() || student.getLastName().isEmpty()) {
            throw new InvalidInputException("Student details are incomplete.");
        }

        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO students (firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhoneNumber());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new InvalidInputException("Failed to add student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void updateStudent(Student student) throws StudentNotFoundException, InvalidInputException {
        if (student == null || student.getStudentId() <= 0) {
            throw new InvalidInputException("Invalid student details.");
        }

        PreparedStatement ps = null;

        try {
            String query = "UPDATE students SET firstName = ?, lastName = ?, email = ?, phoneNumber = ? WHERE studentId = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhoneNumber());
            ps.setInt(5, student.getStudentId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentNotFoundException("Student with ID " + student.getStudentId() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void deleteStudent(int studentId) throws StudentNotFoundException {
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM students WHERE studentId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, studentId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt("studentId"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("email"),
            rs.getString("phoneNumber")
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
