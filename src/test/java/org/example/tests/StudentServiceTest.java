package org.example.tests;

import org.example.Entities.Student;
import org.example.Services.StudentService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentServiceTest {

    private StudentService studentService;

    @Before
    public void setUp() {
        studentService = new StudentService();
    }

    @Test
    public void testAddStudent() {
        Student student = new Student("1001", "Juan Dela Cruz", "juan@email.com");
        studentService.addStudent(student);
        assertEquals(1, studentService.getAllStudents().size());
    }

    @Test
    public void testAddDuplicateStudent() {
        Student student1 = new Student("1001", "Juan Dela Cruz", "juan@email.com");
        Student student2 = new Student("1001", "Pedro Santos", "pedro@email.com");
        studentService.addStudent(student1);
        studentService.addStudent(student2);
        assertEquals(1, studentService.getAllStudents().size());
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student("1001", "Juan Dela Cruz", "juan@email.com");
        studentService.addStudent(student);
        Student found = studentService.getStudentById("1001");
        assertNotNull(found);
        assertEquals("Juan Dela Cruz", found.getName());
    }

    @Test
    public void testGetStudentByIdNotFound() {
        Student found = studentService.getStudentById("9999");
        assertNull(found);
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student("1001", "Juan Dela Cruz", "juan@email.com");
        studentService.addStudent(student);
        studentService.updateStudent("1001", "Juan Updated", "updated@email.com");
        Student updated = studentService.getStudentById("1001");
        assertEquals("Juan Updated", updated.getName());
        assertEquals("updated@email.com", updated.getEmail());
    }

    @Test
    public void testRemoveStudent() {
        Student student = new Student("1001", "Juan Dela Cruz", "juan@email.com");
        studentService.addStudent(student);
        studentService.removeStudent("1001");
        assertEquals(0, studentService.getAllStudents().size());
    }

    @Test
    public void testRemoveNonExistentStudent() {
        studentService.removeStudent("9999");
        assertEquals(0, studentService.getAllStudents().size());
    }
}