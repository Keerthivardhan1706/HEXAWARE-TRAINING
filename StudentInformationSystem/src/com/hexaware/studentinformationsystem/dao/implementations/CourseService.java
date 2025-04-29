package com.hexaware.studentinformationsystem.dao.implementations;

import com.hexaware.studentinformationsystem.dao.interfaces.CourseDAO;
import com.hexaware.studentinformationsystem.entity.Course;
import com.hexaware.studentinformationsystem.exceptions.CourseNotFoundException;

import java.sql.*;

public abstract class CourseService implements CourseDAO {
    private Connection connection;

    public CourseService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Course getCourseById(int courseId) {
        Course course = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM courses WHERE courseId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, courseId);
            rs = ps.executeQuery();

            if (rs.next()) {
                course = mapResultSetToCourse(rs);
            } else {
                throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
            }
        } catch (SQLException | CourseNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return course;
    }

    @Override
    public void addCourse(Course course) {
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO courses (courseName, credits, teacherId) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getCredits());
            ps.setInt(3, course.getTeacherId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void updateCourse(Course course) {
        PreparedStatement ps = null;

        try {
            String query = "UPDATE courses SET courseName = ?, credits = ?, teacherId = ? WHERE courseId = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getCredits());
            ps.setInt(3, course.getTeacherId());
            ps.setInt(4, course.getCourseId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new CourseNotFoundException("Course with ID " + course.getCourseId() + " not found.");
            }
        } catch (SQLException | CourseNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public void deleteCourse(int courseId) throws CourseNotFoundException {
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM courses WHERE courseId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, courseId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        return new Course(
            rs.getInt("courseId"),
            rs.getString("courseName"),
            rs.getInt("credits"),
            rs.getInt("teacherId")
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
