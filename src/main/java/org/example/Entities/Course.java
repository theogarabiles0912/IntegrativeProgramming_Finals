package org.example.Entities;

public class Course {

    // Attributes
    private String courseId;
    private String courseName;
    private int units;
    private double feePerUnit;
    private String prerequisiteCourseId; // null if no prerequisite

    // Constructor
    public Course(String courseId, String courseName, int units, double feePerUnit) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.units = units;
        this.feePerUnit = feePerUnit;
        this.prerequisiteCourseId = null;
    }

    // Getters and Setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }

    public double getFeePerUnit() { return feePerUnit; }
    public void setFeePerUnit(double feePerUnit) { this.feePerUnit = feePerUnit; }

    public String getPrerequisiteCourseId() { return prerequisiteCourseId; }
    public void setPrerequisiteCourseId(String prerequisiteCourseId) { this.prerequisiteCourseId = prerequisiteCourseId; }

    @Override
    public String toString() {
        return "Course [ID: " + courseId + ", Name: " + courseName + ", Units: " + units + ", Fee/Unit: " + feePerUnit + ", Prerequisite: " + (prerequisiteCourseId != null ? prerequisiteCourseId : "None") + "]";
    }
}