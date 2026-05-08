package org.example.Services;

import org.example.Entities.Course;
import org.example.Entities.Department;
import org.example.Entities.Section;
import org.example.Entities.Student;
import org.example.Interfaces.EnrollmentInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollmentService implements EnrollmentInt {

    private List<Section> sections;
    private List<Department> departments;
    private Map<String, Map<String, Double>> studentGrades; // studentId -> (sectionId -> grade)
    private Map<String, List<String>> transcripts; // studentId -> list of transcript entries

    public EnrollmentService(List<Section> sections, List<Department> departments) {
        this.sections = sections;
        this.departments = departments;
        this.studentGrades = new HashMap<>();
        this.transcripts = new HashMap<>();
    }

    @Override
    public void enrollStudentInSection(String studentId, String sectionId) {
        // Find the student
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Error: Student ID " + studentId + " not found!");
            return;
        }

        // Find the section
        Section section = findSectionById(sectionId);
        if (section == null) {
            System.out.println("Error: Section ID " + sectionId + " not found!");
            return;
        }

        // Check if section is full
        if (section.isFull()) {
            System.out.println("Error: Section " + section.getSectionName() + " is already full! (" + section.getCurrentEnrollment() + "/" + section.getMaxCapacity() + ")");
            return;
        }

        // Check if student is already enrolled in this section
        if (section.getEnrolledStudents().contains(student)) {
            System.out.println("Error: Student " + student.getName() + " is already enrolled in section " + section.getSectionName());
            return;
        }

        // Check prerequisite
        if (!checkPrerequisite(studentId, section.getCourse().getCourseId())) {
            System.out.println("Error: Student " + student.getName() + " has not completed the prerequisite for " + section.getCourse().getCourseName());
            return;
        }

        // Enroll the student
        section.getEnrolledStudents().add(student);
        System.out.println("Student " + student.getName() + " successfully enrolled in section " + section.getSectionName());
    }

    @Override
    public void dropStudentFromSection(String studentId, String sectionId) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Error: Student ID " + studentId + " not found!");
            return;
        }

        Section section = findSectionById(sectionId);
        if (section == null) {
            System.out.println("Error: Section ID " + sectionId + " not found!");
            return;
        }

        if (!section.getEnrolledStudents().contains(student)) {
            System.out.println("Error: Student " + student.getName() + " is not enrolled in section " + section.getSectionName());
            return;
        }

        section.getEnrolledStudents().remove(student);
        System.out.println("Student " + student.getName() + " successfully dropped from section " + section.getSectionName());
    }

    @Override
    public void viewDepartmentHierarchy() {
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            return;
        }

        for (Department department : departments) {
            System.out.println("============================================");
            System.out.println("Department: " + department.getDepartmentName());
            System.out.println("============================================");

            if (department.getSections().isEmpty()) {
                System.out.println("  No sections found in this department.");
                continue;
            }

            for (Section section : department.getSections()) {
                System.out.println("  Section: " + section.getSectionName() + " | Course: " + section.getCourse().getCourseName() + " | Schedule: " + section.getSchedule());
                System.out.println("  Instructor: " + (section.getInstructor() != null ? section.getInstructor().getName() : "TBA"));
                System.out.println("  Enrolled: " + section.getCurrentEnrollment() + "/" + section.getMaxCapacity());
                System.out.println("  Students:");

                if (section.getEnrolledStudents().isEmpty()) {
                    System.out.println("    No students enrolled.");
                } else {
                    for (Student student : section.getEnrolledStudents()) {
                        System.out.println("    - " + student.getName() + " | ID: " + student.getStudentId());
                    }
                }
                System.out.println();
            }
        }
    }

    @Override
    public void recordGrade(String studentId, String sectionId, double grade) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Error: Student ID " + studentId + " not found!");
            return;
        }

        Section section = findSectionById(sectionId);
        if (section == null) {
            System.out.println("Error: Section ID " + sectionId + " not found!");
            return;
        }

        if (!section.getEnrolledStudents().contains(student)) {
            System.out.println("Error: Student " + student.getName() + " is not enrolled in section " + section.getSectionName());
            return;
        }

        // Store the grade
        studentGrades.computeIfAbsent(studentId, k -> new HashMap<>()).put(sectionId, grade);

        // Update GPA
        updateGPA(studentId);

        // Add to transcript
        String transcriptEntry = "Course: " + section.getCourse().getCourseName() +
                " | Section: " + section.getSectionName() +
                " | Instructor: " + (section.getInstructor() != null ? section.getInstructor().getName() : "TBA") +
                " | Grade: " + grade;
        transcripts.computeIfAbsent(studentId, k -> new ArrayList<>()).add(transcriptEntry);

        System.out.println("Grade " + grade + " recorded for " + student.getName() + " in " + section.getCourse().getCourseName());
    }

    @Override
    public List<String> getStudentTranscript(String studentId) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Error: Student ID " + studentId + " not found!");
            return new ArrayList<>();
        }

        List<String> transcript = transcripts.getOrDefault(studentId, new ArrayList<>());
        if (transcript.isEmpty()) {
            System.out.println("No transcript found for Student ID " + studentId);
        } else {
            System.out.println("Transcript for " + student.getName() + ":");
            for (String entry : transcript) {
                System.out.println("  - " + entry);
            }
            System.out.println("  Current GPA: " + student.getGpa());
        }
        return transcript;
    }

    @Override
    public boolean checkPrerequisite(String studentId, String courseId) {
        // Find the course
        Course course = findCourseById(courseId);
        if (course == null) return true;

        // If no prerequisite, allow enrollment
        String prerequisiteId = course.getPrerequisiteCourseId();
        if (prerequisiteId == null) return true;

        // Check if student has passed the prerequisite
        Map<String, Double> grades = studentGrades.get(studentId);
        if (grades == null) return false;

        for (Map.Entry<String, Double> entry : grades.entrySet()) {
            Section section = findSectionById(entry.getKey());
            if (section != null && section.getCourse().getCourseId().equals(prerequisiteId)) {
                return entry.getValue() >= 75.0; // Passing grade is 75
            }
        }
        return false;
    }

    // Helper method to update GPA
    private void updateGPA(String studentId) {
        Map<String, Double> grades = studentGrades.get(studentId);
        if (grades == null || grades.isEmpty()) return;

        double total = 0.0;
        for (double grade : grades.values()) {
            total += grade;
        }
        double gpa = total / grades.size();

        Student student = findStudentById(studentId);
        if (student != null) {
            student.setGpa(Math.round(gpa * 100.0) / 100.0);
        }
    }

    // Helper method to find student
    private Student findStudentById(String studentId) {
        for (Section section : sections) {
            for (Student student : section.getEnrolledStudents()) {
                if (student.getStudentId().equals(studentId)) {
                    return student;
                }
            }
        }
        return null;
    }

    // Helper method to find section
    private Section findSectionById(String sectionId) {
        for (Section section : sections) {
            if (section.getSectionId().equals(sectionId)) {
                return section;
            }
        }
        return null;
    }

    // Helper method to find course
    private Course findCourseById(String courseId) {
        for (Section section : sections) {
            if (section.getCourse().getCourseId().equals(courseId)) {
                return section.getCourse();
            }
        }
        return null;
    }
}