package org.example.Interfaces;

import org.example.Entities.Instructor;
import java.util.List;

public interface InstructorInt {

    void addInstructor(Instructor instructor);
    void updateInstructor(String instructorId, String newName, String newEmail);
    void removeInstructor(String instructorId);
    List<Instructor> getAllInstructors();
    Instructor getInstructorById(String instructorId);
    void assignInstructorToSection(String instructorId, String sectionId);
    String getInstructorDetails(String instructorId);
}