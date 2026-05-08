package org.example.Entities;

import java.util.ArrayList;
import java.util.List;

public class TuitionFeePayment {

    // Attributes
    private String paymentId;
    private Student student;
    private double totalFee;
    private double amountPaid;
    private String scholarshipType;
    private List<String> paymentHistory;

    // Constructor
    public TuitionFeePayment(String paymentId, Student student, double totalFee) {
        this.paymentId = paymentId;
        this.student = student;
        this.totalFee = totalFee;
        this.amountPaid = 0.0;
        this.scholarshipType = student.getScholarshipType();
        this.paymentHistory = new ArrayList<>();
    }

    // Get remaining balance
    public double getRemainingBalance() {
        return totalFee - amountPaid;
    }

    // Check if fully paid
    public boolean isFullyPaid() {
        return amountPaid >= totalFee;
    }

    // Apply scholarship discount
    public void applyScholarshipDiscount() {
        switch (scholarshipType) {
            case "ACADEMIC":
                totalFee = totalFee * 0.50; // 50% discount
                paymentHistory.add("Academic Scholarship applied: 50% discount");
                break;
            case "SPORTS":
                totalFee = totalFee * 0.75; // 25% discount
                paymentHistory.add("Sports Scholarship applied: 25% discount");
                break;
            default:
                paymentHistory.add("No scholarship discount applied");
                break;
        }
    }

    // Record a payment
    public void makePayment(double amount) {
        this.amountPaid += amount;
        paymentHistory.add("Payment made: " + amount + " | Remaining: " + getRemainingBalance());
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public double getTotalFee() { return totalFee; }
    public void setTotalFee(double totalFee) { this.totalFee = totalFee; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public String getScholarshipType() { return scholarshipType; }
    public void setScholarshipType(String scholarshipType) { this.scholarshipType = scholarshipType; }

    public List<String> getPaymentHistory() { return paymentHistory; }

    @Override
    public String toString() {
        return "Payment [ID: " + paymentId + ", Student: " + student.getName() +
                ", Total Fee: " + totalFee + ", Amount Paid: " + amountPaid +
                ", Remaining: " + getRemainingBalance() + ", Status: " +
                (isFullyPaid() ? "FULLY PAID" : "PENDING") + "]";
    }
}