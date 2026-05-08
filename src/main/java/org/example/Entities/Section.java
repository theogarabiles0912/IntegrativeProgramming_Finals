package org.example.Entities;

import java.util.ArrayList;
import java.util.List;

public class Section {

    // Attributes
    private String sectionId;
    private String sectionName;
    private int maxCapacity;
    private Course course;
    private Instructor instructor;
    private List<Student> enrolledStudents;
    private String schedule; // e.g., "MWF 8:00-9:00 AM"

    // Constructor
    public Section(String sectionId, String sectionName, int maxCapacity, Course course) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.maxCapacity = maxCapacity;
        this.course = course;
        this.instructor = null;
        this.enrolledStudents = new ArrayList<>();
        this.schedule = "TBA";
    }

    // Check if section is full
    public boolean isFull() {
        return enrolledStudents.size() >= maxCapacity;
    }

    // Get current enrollment count
    public int getCurrentEnrollment() {
        return enrolledStudents.size();
    }

    // Getters and Setters
    public String getSectionId() { return sectionId; }
    public void setSectionId(String sectionId) { this.sectionId = sectionId; }

    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public void setEnrolledStudents(List<Student> enrolledStudents) { this.enrolledStudents = enrolledStudents; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    @Override
    public String toString() {
        return "Section [ID: " + sectionId + ", Name: " + sectionName + ", Course: " + course.getCourseName() + ", Instructor: " + (instructor != null ? instructor.getName() : "TBA") + ", Enrolled: " + getCurrentEnrollment() + "/" + maxCapacity + ", Schedule: " + schedule + "]";
    }
}