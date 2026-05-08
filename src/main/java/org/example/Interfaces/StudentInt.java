package org.example.Interfaces;

import org.example.Entities.Student;
import java.util.List;

public interface StudentInt {

    void addStudent(Student student);
    void updateStudent(String studentId, String newName, String newEmail);
    void removeStudent(String studentId);
    List<Student> getAllStudents();
    Student getStudentById(String studentId);
}
