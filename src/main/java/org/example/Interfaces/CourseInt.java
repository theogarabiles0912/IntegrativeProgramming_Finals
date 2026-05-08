package org.example.Interfaces;

import org.example.Entities.Course;
import java.util.List;

public interface CourseInt {

    void addCourse(Course course);
    void updateCourse(String courseId, String newName, int newUnits, double newFeePerUnit);
    void removeCourse(String courseId);
    List<Course> getAllCourses();
    Course getCourseById(String courseId);
}