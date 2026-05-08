package org.example.Interfaces;

import org.example.Entities.TuitionFeePayment;

public interface TuitionInt {

    double calculateFee(String studentId);
    void makePayment(String studentId, double amount);
    double getRemainingBalance(String studentId);
    void applyScholarshipDiscount(String studentId);
    TuitionFeePayment getPaymentRecord(String studentId);
}