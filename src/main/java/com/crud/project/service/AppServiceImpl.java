package com.crud.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.project.entity.Course;
import com.crud.project.entity.Instructor;
import com.crud.project.interfaces.AppDAO;
import com.crud.project.interfaces.AppService;

import jakarta.transaction.Transactional;


@Service
public class AppServiceImpl implements AppService{
	
	private AppDAO dao;
	
	
	@Autowired
	AppServiceImpl(AppDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Instructor> getAllInstructors() {
		
		return this.dao.getAllInstructors();
	}

	@Override
	@Transactional
	public void saveInstructor(Instructor instructor) {
		this.dao.saveInstructor(instructor);
	}

	@Override
	public Instructor findInstructorById(int id) {
		return this.dao.findInstructorById(id);
	}

	@Override
	@Transactional
	public void deleteInstructorById(int id) {
		this.dao.deleteInstructorById(id);
	}

	@Override
	public List<Course> getAllCoourse() {
		return this.dao.getAllCoourse();
	}

	@Override
	public Course findCourseById(int id) {
		return this.dao.findCourseById(id);
	}

	@Override
	@Transactional
	public void saveCourse(Course course) {
		this.dao.saveCourse(course);
		
	}

	@Override
	@Transactional
	public void deleteCourseById(int id) {
		this.dao.deleteCourseById(id);
	}

}
