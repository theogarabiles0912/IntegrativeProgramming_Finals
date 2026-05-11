package org.example.tests;

import org.example.Entities.Course;
import org.example.Entities.Department;
import org.example.Entities.Section;
import org.example.Entities.Student;
import org.example.Services.EnrollmentService;
import org.example.Services.StudentService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentServiceTest {

    private EnrollmentService enrollmentService;
    private StudentService studentService;
    private List<Section> sections;
    private List<Department> departments;
    private Student student;
    private Section section;

    @Before
    public void setUp() {
        sections = new ArrayList<>();
        departments = new ArrayList<>();
        studentService = new StudentService();
        enrollmentService = new EnrollmentService(sections, departments, studentService);

        // Create and add a student
        student = new Student("1001", "Juan Dela Cruz", "juan@email.com");
        studentService.addStudent(student);

        // Create a course with no prerequisite
        Course course = new Course("101", "Programming 1", 3, 500.0);

        // Create a section with max capacity of 2
        section = new Section("301", "BSIT-1A", 2, course);
        sections.add(section);
    }

    @Test
    public void testEnrollStudentSuccess() {
        enrollmentService.enrollStudentInSection("1001", "301");
        assertEquals(1, section.getEnrolledStudents().size());
    }

    @Test
    public void testEnrollStudentNotFound() {
        enrollmentService.enrollStudentInSection("9999", "301");
        assertEquals(0, section.getEnrolledStudents().size());
    }

    @Test
    public void testEnrollSectionNotFound() {
        enrollmentService.enrollStudentInSection("1001", "9999");
        assertEquals(0, section.getEnrolledStudents().size());
    }

    @Test
    public void testEnrollStudentAlreadyEnrolled() {
        enrollmentService.enrollStudentInSection("1001", "301");
        enrollmentService.enrollStudentInSection("1001", "301");
        assertEquals(1, section.getEnrolledStudents().size());
    }

    @Test
    public void testSectionFullCapacity() {
        // Fill the section to max capacity of 2
        Student student2 = new Student("1002", "Maria Santos", "maria@email.com");
        Student student3 = new Student("1003", "Pedro Reyes", "pedro@email.com");
        studentService.addStudent(student2);
        studentService.addStudent(student3);

        enrollmentService.enrollStudentInSection("1001", "301");
        enrollmentService.enrollStudentInSection("1002", "301");

        // This should be rejected since section is full
        enrollmentService.enrollStudentInSection("1003", "301");
        assertEquals(2, section.getEnrolledStudents().size());
    }

    @Test
    public void testDropStudentSuccess() {
        enrollmentService.enrollStudentInSection("1001", "301");
        enrollmentService.dropStudentFromSection("1001", "301");
        assertEquals(0, section.getEnrolledStudents().size());
    }

    @Test
    public void testDropStudentNotEnrolled() {
        enrollmentService.dropStudentFromSection("1001", "301");
        assertEquals(0, section.getEnrolledStudents().size());
    }

    @Test
    public void testRecordGrade() {
        enrollmentService.enrollStudentInSection("1001", "301");
        enrollmentService.recordGrade("1001", "301", 90.0);
        assertEquals(90.0, student.getGpa(), 0.01);
    }

    @Test
    public void testPrerequisiteNotMet() {
        // Create a course with a prerequisite
        Course advancedCourse = new Course("102", "Programming 2", 3, 500.0);
        advancedCourse.setPrerequisiteCourseId("101");

        Section advancedSection = new Section("302", "BSIT-2A", 30, advancedCourse);
        sections.add(advancedSection);

        // Student has not passed prerequisite so enrollment should fail
        enrollmentService.enrollStudentInSection("1001", "302");
        assertEquals(0, advancedSection.getEnrolledStudents().size());
    }

    @Test
    public void testPrerequisiteMet() {
        // Enroll and pass the prerequisite course first
        enrollmentService.enrollStudentInSection("1001", "301");
        enrollmentService.recordGrade("1001", "301", 85.0);

        // Create advanced course with prerequisite
        Course advancedCourse = new Course("102", "Programming 2", 3, 500.0);
        advancedCourse.setPrerequisiteCourseId("101");

        Section advancedSection = new Section("302", "BSIT-2A", 30, advancedCourse);
        sections.add(advancedSection);

        // Now student should be able to enroll
        enrollmentService.enrollStudentInSection("1001", "302");
        assertEquals(1, advancedSection.getEnrolledStudents().size());
    }
}