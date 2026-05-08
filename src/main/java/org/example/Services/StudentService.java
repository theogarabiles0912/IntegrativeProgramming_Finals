package org.example.Services;

import org.example.Entities.Student;
import org.example.Interfaces.StudentInt;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentInt {

    private List<Student> students = new ArrayList<>();

    @Override
    public void addStudent(Student student) {
        // Check for duplicate student ID
        for (Student s : students) {
            if (s.getStudentId().equals(student.getStudentId())) {
                System.out.println(" Error: Student ID " + student.getStudentId() + " already exists!");
                return;
            }
        }
        students.add(student);
        System.out.println(" Student added successfully: " + student.getName());
    }

    @Override
    public void updateStudent(String studentId, String newName, String newEmail) {
        Student student = getStudentById(studentId);
        if (student != null) {
            student.setName(newName);
            student.setEmail(newEmail);
            System.out.println(" Student updated successfully: " + student.getName());
        } else {
            System.out.println(" Error: Student ID " + studentId + " not found!");
        }
    }

    @Override
    public void removeStudent(String studentId) {
        Student student = getStudentById(studentId);
        if (student != null) {
            students.remove(student);
            System.out.println(" Student removed successfully: " + studentId);
        } else {
            System.out.println(" Error: Student ID " + studentId + " not found!");
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return students;
    }

    @Override
    public Student getStudentById(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }
}