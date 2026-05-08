package org.example.Interfaces;

import java.util.List;

public interface EnrollmentInt {

    void enrollStudentInSection(String studentId, String sectionId);
    void dropStudentFromSection(String studentId, String sectionId);
    void viewDepartmentHierarchy();
    void recordGrade(String studentId, String sectionId, double grade);
    List<String> getStudentTranscript(String studentId);
    boolean checkPrerequisite(String studentId, String courseId);
}