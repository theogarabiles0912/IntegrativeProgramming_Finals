package org.example.Services;

import org.example.Entities.Instructor;
import org.example.Entities.Section;
import org.example.Interfaces.InstructorInt;
import java.util.ArrayList;
import java.util.List;

public class InstructorService implements InstructorInt {

    private List<Instructor> instructors = new ArrayList<>();
    private List<Section> sections;

    public InstructorService(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public void addInstructor(Instructor instructor) {
        for (Instructor i : instructors) {
            if (i.getInstructorId().equals(instructor.getInstructorId())) {
                System.out.println("Error: Instructor ID " + instructor.getInstructorId() + " already exists!");
                return;
            }
        }
        instructors.add(instructor);
        System.out.println("Instructor added successfully: " + instructor.getName());
    }

    @Override
    public void updateInstructor(String instructorId, String newName, String newEmail) {
        Instructor instructor = getInstructorById(instructorId);
        if (instructor != null) {
            instructor.setName(newName);
            instructor.setEmail(newEmail);
            System.out.println("Instructor updated successfully: " + instructor.getName());
        } else {
            System.out.println("Error: Instructor ID " + instructorId + " not found!");
        }
    }

    @Override
    public void removeInstructor(String instructorId) {
        Instructor instructor = getInstructorById(instructorId);
        if (instructor != null) {
            instructors.remove(instructor);
            System.out.println("Instructor removed successfully: " + instructorId);
        } else {
            System.out.println("Error: Instructor ID " + instructorId + " not found!");
        }
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructors;
    }

    @Override
    public Instructor getInstructorById(String instructorId) {
        for (Instructor i : instructors) {
            if (i.getInstructorId().equals(instructorId)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void assignInstructorToSection(String instructorId, String sectionId) {
        Instructor instructor = getInstructorById(instructorId);
        if (instructor == null) {
            System.out.println("Error: Instructor ID " + instructorId + " not found!");
            return;
        }

        for (Section section : sections) {
            if (section.getSectionId().equals(sectionId)) {
                section.setInstructor(instructor);
                System.out.println("Instructor " + instructor.getName() + " assigned to section " + section.getSectionName());
                return;
            }
        }
        System.out.println("Error: Section ID " + sectionId + " not found!");
    }

    @Override
    public String getInstructorDetails(String instructorId) {
        Instructor instructor = getInstructorById(instructorId);
        if (instructor != null) {
            return instructor.toString();
        }
        return "Error: Instructor ID " + instructorId + " not found!";
    }
}
