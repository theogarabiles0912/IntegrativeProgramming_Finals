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
    private Map<String, Map<String, Double>> studentGrades;
    private Map<String, List<String>> transcripts;
    private StudentService studentService;

    public EnrollmentService(List<Section> sections, List<Department> departments, StudentService studentService) {
        this.sections = sections;
        this.departments = departments;
        this.studentGrades = new HashMap<>();
        this.transcripts = new HashMap<>();
        this.studentService = studentService;
    }

    @Override
    public void enrollStudentInSection(String studentId, String sectionId) {
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

        if (section.isFull()) {
            System.out.println("Error: Section " + section.getSectionName() + " is already full! (" + section.getCurrentEnrollment() + "/" + section.getMaxCapacity() + ")");
            return;
        }

        if (section.getEnrolledStudents().contains(student)) {
            System.out.println("Error: Student " + student.getName() + " is already enrolled in section " + section.getSectionName());
            return;
        }

        if (!checkPrerequisite(studentId, section.getCourse().getCourseId())) {
            System.out.println("Error: Student " + student.getName() + " has not completed the prerequisite for " + section.getCourse().getCourseName());
            return;
        }

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

        studentGrades.computeIfAbsent(studentId, k -> new HashMap<>()).put(sectionId, grade);
        updateGPA(studentId);

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

        // Check if student is enrolled in any section
        boolean isEnrolled = false;
        List<String> enrolledSections = new ArrayList<>();
        for (Section section : sections) {
            if (section.getEnrolledStudents().contains(student)) {
                isEnrolled = true;
                enrolledSections.add("  - " + section.getSectionName() + " | Course: " + section.getCourse().getCourseName() + " | Schedule: " + section.getSchedule());
            }
        }

        System.out.println("============================================");
        System.out.println("Transcript for: " + student.getName() + " | ID: " + student.getStudentId());
        System.out.println("============================================");

        if (!isEnrolled) {
            System.out.println("This student is not enrolled in any section.");
        } else {
            System.out.println("Currently Enrolled In:");
            for (String s : enrolledSections) {
                System.out.println(s);
            }
        }

        List<String> transcript = transcripts.getOrDefault(studentId, new ArrayList<>());
        if (transcript.isEmpty()) {
            System.out.println("No grade records found.");
        } else {
            System.out.println("Grade Records:");
            for (String entry : transcript) {
                System.out.println("  - " + entry);
            }
            System.out.println("Current GPA: " + student.getGpa());
        }

        System.out.println("============================================");
        return transcript;
    }

    @Override
    public boolean checkPrerequisite(String studentId, String courseId) {
        Course course = findCourseById(courseId);
        if (course == null) return true;

        String prerequisiteId = course.getPrerequisiteCourseId();
        if (prerequisiteId == null) return true;

        Map<String, Double> grades = studentGrades.get(studentId);
        if (grades == null) return false;

        for (Map.Entry<String, Double> entry : grades.entrySet()) {
            Section section = findSectionById(entry.getKey());
            if (section != null && section.getCourse().getCourseId().equals(prerequisiteId)) {
                return entry.getValue() >= 75.0;
            }
        }
        return false;
    }

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

    private Student findStudentById(String studentId) {
        return studentService.getStudentById(studentId);
    }

    private Section findSectionById(String sectionId) {
        for (Section section : sections) {
            if (section.getSectionId().equals(sectionId)) {
                return section;
            }
        }
        return null;
    }

    private Course findCourseById(String courseId) {
        for (Section section : sections) {
            if (section.getCourse().getCourseId().equals(courseId)) {
                return section.getCourse();
            }
        }
        return null;
    }
}