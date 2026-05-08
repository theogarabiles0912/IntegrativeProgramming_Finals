package org.example.Entities;

public class Instructor {

    // Attributes
    private String instructorId;
    private String name;
    private String email;
    private String department;

    // Constructor
    public Instructor(String instructorId, String name, String email, String department) {
        this.instructorId = instructorId;
        this.name = name;
        this.email = email;
        this.department = department;
    }

    // Getters and Setters
    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "Instructor [ID: " + instructorId + ", Name: " + name + ", Email: " + email + ", Department: " + department + "]";
    }
}