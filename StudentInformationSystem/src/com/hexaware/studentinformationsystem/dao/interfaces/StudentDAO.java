package com.hexaware.studentinformationsystem.dao.interfaces;


import com.hexaware.studentinformationsystem.entity.Student;
import java.util.List;

public interface StudentDAO {
    void addStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(int studentId);
    Student getStudentById(int studentId);
    List<Student> getAllStudents();
}
