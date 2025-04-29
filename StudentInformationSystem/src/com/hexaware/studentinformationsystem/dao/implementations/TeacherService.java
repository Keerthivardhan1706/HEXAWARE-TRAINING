package com.hexaware.studentinformationsystem.dao.implementations;

import com.hexaware.studentinformationsystem.dao.interfaces.TeacherDAO;
import com.hexaware.studentinformationsystem.entity.Teacher;
import com.hexaware.studentinformationsystem.exception.TeacherNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherService implements TeacherDAO {
    private Connection connection;

    public TeacherService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Teacher getTeacherById(int teacherId) throws TeacherNotFoundException {
        Teacher teacher = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM teachers WHERE teacherId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, teacherId);
            rs = ps.executeQuery();

            if (rs.next()) {
                teacher = mapResultSetToTeacher(rs);
            } else {
                throw new TeacherNotFoundException("Teacher with ID " + teacherId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return teacher;
    }

    @Override
    public void addTeacher(Teacher teacher) {
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO teachers (firstName, lastName, email, expertise) VALUES (?, ?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, teacher.getFirstName());
            ps.setString(2, teacher.getLastName());
            ps.setString(3, teacher.getEmail());
            ps.setString(4, teacher.getExpertise());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void updateTeacher(Teacher teacher) throws TeacherNotFoundException {
        PreparedStatement ps = null;

        try {
            String query = "UPDATE teachers SET firstName = ?, lastName = ?, email = ?, expertise = ? WHERE teacherId = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, teacher.getFirstName());
            ps.setString(2, teacher.getLastName());
            ps.setString(3, teacher.getEmail());
            ps.setString(4, teacher.getExpertise());
            ps.setInt(5, teacher.getTeacherId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new TeacherNotFoundException("Teacher with ID " + teacher.getTeacherId() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void deleteTeacher(int teacherId) throws TeacherNotFoundException {
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM teachers WHERE teacherId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, teacherId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new TeacherNotFoundException("Teacher with ID " + teacherId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public List<Course> getAssignedCourses(int teacherId) {
        List<Course> courses = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM courses WHERE teacherId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, teacherId);
            rs = ps.executeQuery();

            while (rs.next()) {
                courses.add(new Course(
                    rs.getInt("courseId"),
                    rs.getString("courseName"),
                    rs.getInt("credits"),
                    rs.getInt("teacherId")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return courses;
    }

    private Teacher mapResultSetToTeacher(ResultSet rs) throws SQLException {
        return new Teacher(
            rs.getInt("teacherId"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("email"),
            rs.getString("expertise")
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
