package com.hexaware.studentinformationsystem.dao.interfaces;



import com.hexaware.studentinformationsystem.entity.Teacher;
import java.util.List;

public interface TeacherDAO {
    void addTeacher(Teacher teacher);                  
    void updateTeacherInfo(int teacherId, String name, String email, String expertise);  
    void deleteTeacher(int teacherId);                 
    Teacher getTeacherById(int teacherId);             
    List<Teacher> getAllTeachers();                   
    List<Teacher> getAssignedCourses(int teacherId);    
}
