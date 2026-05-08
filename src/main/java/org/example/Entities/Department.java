package org.example.Entities;

import java.util.ArrayList;
import java.util.List;

public class Department {

    // Attributes
    private String departmentId;
    private String departmentName;
    private List<Section> sections;

    // Constructor
    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.sections = new ArrayList<>();
    }

    // Add a section to this department
    public void addSection(Section section) {
        sections.add(section);
    }

    // Remove a section from this department
    public void removeSection(Section section) {
        sections.remove(section);
    }

    // Getters and Setters
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public List<Section> getSections() { return sections; }
    public void setSections(List<Section> sections) { this.sections = sections; }

    @Override
    public String toString() {
        return "Department [ID: " + departmentId + ", Name: " + departmentName + ", Sections: " + sections.size() + "]";
    }
}