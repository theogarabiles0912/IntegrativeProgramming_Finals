package org.example.Entities;

public class Student {

    // Attributes
    private String studentId;
    private String name;
    private String email;
    private double gpa;
    private String scholarshipType; // "ACADEMIC", "SPORTS", or "NONE"

    // Constructor
    public Student(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.gpa = 0.0;
        this.scholarshipType = "NONE";
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getScholarshipType() { return scholarshipType; }
    public void setScholarshipType(String scholarshipType) { this.scholarshipType = scholarshipType; }

    @Override
    public String toString() {
        return "Student [ID: " + studentId + ", Name: " + name + ", Email: " + email + ", GPA: " + gpa + ", Scholarship: " + scholarshipType + "]";
    }
}