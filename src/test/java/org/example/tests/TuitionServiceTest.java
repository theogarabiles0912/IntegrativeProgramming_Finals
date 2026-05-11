package org.example.tests;

import org.example.Entities.Course;
import org.example.Entities.Section;
import org.example.Entities.Student;
import org.example.Services.TuitionService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TuitionServiceTest {

    private TuitionService tuitionService;
    private List<Section> sections;
    private Student student;

    @Before
    public void setUp() {
        sections = new ArrayList<>();
        tuitionService = new TuitionService(sections);

        // Create a student
        student = new Student("2001", "Maria Santos", "maria@email.com");

        // Create a course
        Course course = new Course("101", "Programming 1", 3, 500.0);

        // Create a section and enroll the student
        Section section = new Section("301", "BSIT-1A", 30, course);
        section.getEnrolledStudents().add(student);
        sections.add(section);
    }

    @Test
    public void testCalculateFee() {
        double fee = tuitionService.calculateFee("2001");
        // 3 units x 500.0 per unit = 1500.0
        assertEquals(1500.0, fee, 0.01);
    }

    @Test
    public void testCreatePaymentRecord() {
        tuitionService.createPaymentRecord(student);
        assertNotNull(tuitionService.getPaymentRecord("2001"));
    }

    @Test
    public void testMakePayment() {
        tuitionService.createPaymentRecord(student);
        tuitionService.makePayment("2001", 500.0);
        assertEquals(1000.0, tuitionService.getRemainingBalance("2001"), 0.01);
    }

    @Test
    public void testFullPayment() {
        tuitionService.createPaymentRecord(student);
        tuitionService.makePayment("2001", 1500.0);
        assertEquals(0.0, tuitionService.getRemainingBalance("2001"), 0.01);
        assertTrue(tuitionService.getPaymentRecord("2001").isFullyPaid());
    }

    @Test
    public void testAcademicScholarshipDiscount() {
        student.setScholarshipType("ACADEMIC");
        tuitionService.createPaymentRecord(student);
        tuitionService.applyScholarshipDiscount("2001");
        // 50% discount on 1500.0 = 750.0
        assertEquals(750.0, tuitionService.getPaymentRecord("2001").getTotalFee(), 0.01);
    }

    @Test
    public void testSportsScholarshipDiscount() {
        student.setScholarshipType("SPORTS");
        tuitionService.createPaymentRecord(student);
        tuitionService.applyScholarshipDiscount("2001");
        // 25% discount on 1500.0 = 1125.0
        assertEquals(1125.0, tuitionService.getPaymentRecord("2001").getTotalFee(), 0.01);
    }

    @Test
    public void testNoScholarshipDiscount() {
        student.setScholarshipType("NONE");
        tuitionService.createPaymentRecord(student);
        tuitionService.applyScholarshipDiscount("2001");
        // No discount, fee remains 1500.0
        assertEquals(1500.0, tuitionService.getPaymentRecord("2001").getTotalFee(), 0.01);
    }
}