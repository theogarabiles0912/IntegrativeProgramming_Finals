package org.example.Services;

import org.example.Entities.Course;
import org.example.Entities.Section;
import org.example.Entities.Student;
import org.example.Entities.TuitionFeePayment;
import org.example.Interfaces.TuitionInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuitionService implements TuitionInt {

    private Map<String, TuitionFeePayment> paymentRecords = new HashMap<>();
    private List<Section> sections;

    public TuitionService(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public double calculateFee(String studentId) {
        double totalFee = 0.0;
        for (Section section : sections) {
            for (Student student : section.getEnrolledStudents()) {
                if (student.getStudentId().equals(studentId)) {
                    Course course = section.getCourse();
                    totalFee += course.getUnits() * course.getFeePerUnit();
                }
            }
        }
        return totalFee;
    }

    @Override
    public void makePayment(String studentId, double amount) {
        TuitionFeePayment payment = getPaymentRecord(studentId);
        if (payment == null) {
            System.out.println("Error: No payment record found for Student ID " + studentId);
            return;
        }
        if (payment.isFullyPaid()) {
            System.out.println("Notice: Student " + studentId + " has already fully paid their tuition.");
            return;
        }
        payment.makePayment(amount);
        System.out.println("Payment of " + amount + " recorded successfully.");
        System.out.println("Remaining balance: " + payment.getRemainingBalance());
    }

    @Override
    public double getRemainingBalance(String studentId) {
        TuitionFeePayment payment = getPaymentRecord(studentId);
        if (payment == null) {
            System.out.println("Error: No payment record found for Student ID " + studentId);
            return 0.0;
        }
        return payment.getRemainingBalance();
    }

    @Override
    public void applyScholarshipDiscount(String studentId) {
        TuitionFeePayment payment = getPaymentRecord(studentId);
        if (payment == null) {
            System.out.println("Error: No payment record found for Student ID " + studentId);
            return;
        }
        payment.applyScholarshipDiscount();
        System.out.println("Scholarship discount applied. New total fee: " + payment.getTotalFee());
    }

    @Override
    public TuitionFeePayment getPaymentRecord(String studentId) {
        return paymentRecords.get(studentId);
    }

    // Create a new payment record for a student
    public void createPaymentRecord(Student student) {
        if (paymentRecords.containsKey(student.getStudentId())) {
            System.out.println("Notice: Payment record already exists for Student ID " + student.getStudentId());
            return;
        }
        double totalFee = calculateFee(student.getStudentId());
        TuitionFeePayment payment = new TuitionFeePayment(
                "PAY-" + student.getStudentId(),
                student,
                totalFee
        );
        paymentRecords.put(student.getStudentId(), payment);
        System.out.println("Payment record created for: " + student.getName() + " | Total Fee: " + totalFee);
    }

    // Display full payment details
    public void displayPaymentRecord(String studentId) {
        TuitionFeePayment payment = getPaymentRecord(studentId);
        if (payment != null) {
            System.out.println(payment.toString());
            System.out.println("Payment History:");
            for (String history : payment.getPaymentHistory()) {
                System.out.println("  - " + history);
            }
        } else {
            System.out.println("Error: No payment record found for Student ID " + studentId);
        }
    }
}