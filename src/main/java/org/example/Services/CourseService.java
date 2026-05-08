package org.example.Services;

import org.example.Entities.Course;
import org.example.Interfaces.CourseInt;
import java.util.ArrayList;
import java.util.List;

public class CourseService implements CourseInt {

    private List<Course> courses = new ArrayList<>();

    @Override
    public void addCourse(Course course) {
        for (Course c : courses) {
            if (c.getCourseId().equals(course.getCourseId())) {
                System.out.println("Error: Course ID " + course.getCourseId() + " already exists!");
                return;
            }
        }
        courses.add(course);
        System.out.println("Course added successfully: " + course.getCourseName());
    }

    @Override
    public void updateCourse(String courseId, String newName, int newUnits, double newFeePerUnit) {
        Course course = getCourseById(courseId);
        if (course != null) {
            course.setCourseName(newName);
            course.setUnits(newUnits);
            course.setFeePerUnit(newFeePerUnit);
            System.out.println("Course updated successfully: " + course.getCourseName());
        } else {
            System.out.println("Error: Course ID " + courseId + " not found!");
        }
    }

    @Override
    public void removeCourse(String courseId) {
        Course course = getCourseById(courseId);
        if (course != null) {
            courses.remove(course);
            System.out.println("Course removed successfully: " + courseId);
        } else {
            System.out.println("Error: Course ID " + courseId + " not found!");
        }
    }

    @Override
    public List<Course> getAllCourses() {
        return courses;
    }

    @Override
    public Course getCourseById(String courseId) {
        for (Course c : courses) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }
}