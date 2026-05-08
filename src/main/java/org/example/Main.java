package org.example;

import org.example.Entities.*;
import org.example.Services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Shared data lists
    static List<Section> sections = new ArrayList<>();
    static List<Department> departments = new ArrayList<>();

    // Services
    static StudentService studentService = new StudentService();
    static InstructorService instructorService = new InstructorService(sections);
    static CourseService courseService = new CourseService();
    static TuitionService tuitionService = new TuitionService(sections);
    static EnrollmentService enrollmentService = new EnrollmentService(sections, departments);

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   Welcome to the School Enrollment System  ");
        System.out.println("============================================");

        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1: studentMenu(); break;
                case 2: instructorMenu(); break;
                case 3: courseMenu(); break;
                case 4: enrollmentMenu(); break;
                case 5: tuitionMenu(); break;
                case 6: departmentMenu(); break;
                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void showMainMenu() {
        System.out.println("\n============================================");
        System.out.println("                 MAIN MENU                 ");
        System.out.println("============================================");
        System.out.println("[1] Student Management");
        System.out.println("[2] Instructor Management");
        System.out.println("[3] Course Management");
        System.out.println("[4] Enrollment Management");
        System.out.println("[5] Tuition Management");
        System.out.println("[6] View Department Hierarchy");
        System.out.println("[0] Exit");
        System.out.println("============================================");
    }

    // =================== STUDENT MENU ===================
    static void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("[1] Add Student");
            System.out.println("[2] Update Student");
            System.out.println("[3] Remove Student");
            System.out.println("[4] View All Students");
            System.out.println("[5] View Student Transcript");
            System.out.println("[0] Back");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.println("Scholarship Type: [1] None  [2] Academic  [3] Sports");
                    int schChoice = getIntInput("Enter choice: ");
                    String scholarship = "NONE";
                    if (schChoice == 2) scholarship = "ACADEMIC";
                    else if (schChoice == 3) scholarship = "SPORTS";
                    Student student = new Student(id, name, email);
                    student.setScholarshipType(scholarship);
                    studentService.addStudent(student);
                    break;
                case 2:
                    System.out.print("Enter Student ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Email: ");
                    String newEmail = scanner.nextLine();
                    studentService.updateStudent(updateId, newName, newEmail);
                    break;
                case 3:
                    System.out.print("Enter Student ID to remove: ");
                    String removeId = scanner.nextLine();
                    studentService.removeStudent(removeId);
                    break;
                case 4:
                    if (studentService.getAllStudents().isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        for (Student s : studentService.getAllStudents()) {
                            System.out.println(s.toString());
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter Student ID: ");
                    String transcriptId = scanner.nextLine();
                    enrollmentService.getStudentTranscript(transcriptId);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // =================== INSTRUCTOR MENU ===================
    static void instructorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Instructor Management ---");
            System.out.println("[1] Add Instructor");
            System.out.println("[2] Update Instructor");
            System.out.println("[3] Remove Instructor");
            System.out.println("[4] View All Instructors");
            System.out.println("[5] Assign Instructor to Section");
            System.out.println("[0] Back");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    System.out.print("Enter Instructor ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = scanner.nextLine();
                    instructorService.addInstructor(new Instructor(id, name, email, dept));
                    break;
                case 2:
                    System.out.print("Enter Instructor ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Email: ");
                    String newEmail = scanner.nextLine();
                    instructorService.updateInstructor(updateId, newName, newEmail);
                    break;
                case 3:
                    System.out.print("Enter Instructor ID to remove: ");
                    String removeId = scanner.nextLine();
                    instructorService.removeInstructor(removeId);
                    break;
                case 4:
                    if (instructorService.getAllInstructors().isEmpty()) {
                        System.out.println("No instructors found.");
                    } else {
                        for (Instructor i : instructorService.getAllInstructors()) {
                            System.out.println(i.toString());
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter Instructor ID: ");
                    String instrId = scanner.nextLine();
                    System.out.print("Enter Section ID: ");
                    String sectId = scanner.nextLine();
                    instructorService.assignInstructorToSection(instrId, sectId);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // =================== COURSE MENU ===================
    static void courseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Course Management ---");
            System.out.println("[1] Add Course");
            System.out.println("[2] Update Course");
            System.out.println("[3] Remove Course");
            System.out.println("[4] View All Courses");
            System.out.println("[0] Back");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    System.out.print("Enter Course ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Course Name: ");
                    String name = scanner.nextLine();
                    int units = getIntInput("Enter Units: ");
                    double fee = getDoubleInput("Enter Fee Per Unit: ");
                    Course course = new Course(id, name, units, fee);
                    System.out.print("Enter Prerequisite Course ID (or press Enter to skip): ");
                    String prereq = scanner.nextLine();
                    if (!prereq.isEmpty()) {
                        course.setPrerequisiteCourseId(prereq);
                    }
                    courseService.addCourse(course);
                    break;
                case 2:
                    System.out.print("Enter Course ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new Course Name: ");
                    String newName = scanner.nextLine();
                    int newUnits = getIntInput("Enter new Units: ");
                    double newFee = getDoubleInput("Enter new Fee Per Unit: ");
                    courseService.updateCourse(updateId, newName, newUnits, newFee);
                    break;
                case 3:
                    System.out.print("Enter Course ID to remove: ");
                    String removeId = scanner.nextLine();
                    courseService.removeCourse(removeId);
                    break;
                case 4:
                    if (courseService.getAllCourses().isEmpty()) {
                        System.out.println("No courses found.");
                    } else {
                        for (Course c : courseService.getAllCourses()) {
                            System.out.println(c.toString());
                        }
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // =================== ENROLLMENT MENU ===================
    static void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("[1] Add Section");
            System.out.println("[2] Enroll Student in Section");
            System.out.println("[3] Drop Student from Section");
            System.out.println("[4] Record Grade");
            System.out.println("[0] Back");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    System.out.print("Enter Section ID: ");
                    String sectionId = scanner.nextLine();
                    System.out.print("Enter Section Name: ");
                    String sectionName = scanner.nextLine();
                    int capacity = getIntInput("Enter Max Capacity: ");
                    System.out.print("Enter Course ID: ");
                    String courseId = scanner.nextLine();
                    Course course = courseService.getCourseById(courseId);
                    if (course == null) {
                        System.out.println("Error: Course ID " + courseId + " not found!");
                        break;
                    }
                    System.out.print("Enter Schedule (e.g. MWF 8:00-9:00 AM): ");
                    String schedule = scanner.nextLine();
                    System.out.print("Enter Department ID: ");
                    String deptId = scanner.nextLine();
                    Section section = new Section(sectionId, sectionName, capacity, course);
                    section.setSchedule(schedule);
                    sections.add(section);
                    // Add to department
                    boolean deptFound = false;
                    for (Department d : departments) {
                        if (d.getDepartmentId().equals(deptId)) {
                            d.addSection(section);
                            deptFound = true;
                            break;
                        }
                    }
                    if (!deptFound) {
                        System.out.println("Notice: Department ID " + deptId + " not found. Section created but not assigned to a department.");
                    } else {
                        System.out.println("Section " + sectionName + " added successfully.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Section ID: ");
                    String sectId = scanner.nextLine();
                    enrollmentService.enrollStudentInSection(studentId, sectId);
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    String dropStudentId = scanner.nextLine();
                    System.out.print("Enter Section ID: ");
                    String dropSectId = scanner.nextLine();
                    enrollmentService.dropStudentFromSection(dropStudentId, dropSectId);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    String gradeStudentId = scanner.nextLine();
                    System.out.print("Enter Section ID: ");
                    String gradeSectId = scanner.nextLine();
                    double grade = getDoubleInput("Enter Grade (0-100): ");
                    enrollmentService.recordGrade(gradeStudentId, gradeSectId, grade);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // =================== TUITION MENU ===================
    static void tuitionMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Tuition Management ---");
            System.out.println("[1] Create Payment Record");
            System.out.println("[2] Apply Scholarship Discount");
            System.out.println("[3] Make Payment");
            System.out.println("[4] View Remaining Balance");
            System.out.println("[5] View Payment Record");
            System.out.println("[0] Back");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    Student student = studentService.getStudentById(studentId);
                    if (student == null) {
                        System.out.println("Error: Student ID " + studentId + " not found!");
                        break;
                    }
                    tuitionService.createPaymentRecord(student);
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String discountId = scanner.nextLine();
                    tuitionService.applyScholarshipDiscount(discountId);
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    String payId = scanner.nextLine();
                    double amount = getDoubleInput("Enter Payment Amount: ");
                    tuitionService.makePayment(payId, amount);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    String balId = scanner.nextLine();
                    System.out.println("Remaining Balance: " + tuitionService.getRemainingBalance(balId));
                    break;
                case 5:
                    System.out.print("Enter Student ID: ");
                    String recId = scanner.nextLine();
                    tuitionService.displayPaymentRecord(recId);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // =================== DEPARTMENT MENU ===================
    static void departmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Department Management ---");
            System.out.println("[1] Add Department");
            System.out.println("[2] View Department Hierarchy");
            System.out.println("[0] Back");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    System.out.print("Enter Department ID: ");
                    String deptId = scanner.nextLine();
                    System.out.print("Enter Department Name: ");
                    String deptName = scanner.nextLine();
                    departments.add(new Department(deptId, deptName));
                    System.out.println("Department " + deptName + " added successfully.");
                    break;
                case 2:
                    enrollmentService.viewDepartmentHierarchy();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // =================== INPUT HELPERS ===================
    static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}