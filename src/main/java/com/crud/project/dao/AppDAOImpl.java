package com.crud.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.crud.project.entity.Course;
import com.crud.project.entity.Instructor;
import com.crud.project.exceptions.EntityNotFoundException;
import com.crud.project.interfaces.AppDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


@Repository
public class AppDAOImpl implements AppDAO{
	
	private EntityManager entityManager;
	
	@Autowired
	public AppDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public void saveInstructor(Instructor instructor) {
		
		this.entityManager.merge(instructor);
	
	}

	@Override
	public List<Instructor> getAllInstructors() {
		
		TypedQuery<Instructor> query = this.entityManager.createQuery("FROM Instructor", Instructor.class);
		
		return query.getResultList();
	}

	@Override
	public Instructor findInstructorById(int id) {
		Instructor instructor = this.entityManager.find(Instructor.class, id);
		
		if(instructor == null) {
			throw new EntityNotFoundException("Instructor not found!");
		}
		
		return instructor;
	}

	@Override
	public void deleteInstructorById(int id) {
		
		Instructor instructor = this.entityManager.find(Instructor.class, id);
		
		if(instructor == null) {
			throw new EntityNotFoundException("Instructor not found!");
		}
		
		this.entityManager.remove(instructor);
		
	}

	@Override
	public List<Course> getAllCoourse() {
		TypedQuery<Course> query = this.entityManager.createQuery("FROM Course", Course.class);
		
		return query.getResultList();
	}

	@Override
	public Course findCourseById(int id) {
		Course course = this.entityManager.find(Course.class, id);
		
		if(course == null) {
			throw new EntityNotFoundException("Course not found!");
		}
		
		return course;
	}

	@Override
	@Transactional
	public void saveCourse(Course course) {
		this.entityManager.merge(course);
		
	}

	@Override
	@Transactional
	public void deleteCourseById(int id) {
		
		Course course = this.entityManager.find(Course.class, id);
		
		if(course == null) {
			throw new EntityNotFoundException("Course not found!");
		}
		
		this.entityManager.remove(course);
		
	}

}
