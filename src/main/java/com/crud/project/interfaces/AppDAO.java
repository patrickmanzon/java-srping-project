package com.crud.project.interfaces;

import java.util.List;

import com.crud.project.entity.Course;
import com.crud.project.entity.Instructor;

public interface AppDAO {
	
	
	List<Instructor> getAllInstructors();	
	
	Instructor findInstructorById(int id);
	
	void saveInstructor(Instructor instructor);
	
	void deleteInstructorById(int id);
	
	List<Course> getAllCoourse();
	
	Course findCourseById(int id);
	
	void saveCourse(Course course);
	
	void deleteCourseById(int id);
	
}
