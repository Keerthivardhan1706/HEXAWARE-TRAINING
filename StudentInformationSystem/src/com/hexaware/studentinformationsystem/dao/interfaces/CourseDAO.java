package com.hexaware.studentinformationsystem.dao.interfaces;

import com.hexaware.studentinformationsystem.entity.Course;
import com.hexaware.studentinformationsystem.exceptions.CourseNotFoundException;

import java.util.List;

public interface CourseDAO {
    void addCourse(Course course);
    void updateCourse(Course course);
    void deleteCourse(int courseId) throws CourseNotFoundException;
    Course getCourseById(int courseId);
    List<Course> getAllCourses();
}
