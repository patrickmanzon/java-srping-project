package com.crud.project.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.project.entity.Course;
import com.crud.project.entity.Instructor;
import com.crud.project.interfaces.AppService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
	
	private AppService service;
	
	CourseController(AppService service) {
		this.service = service;
	}
	
	@GetMapping("")
	ResponseEntity<?> getAll() {
		
		List<Course> courses = this.service.getAllCoourse();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		if(!courses.isEmpty()) {
			map.put("success", true);
			map.put("data", courses);
			
			return new ResponseEntity<>(map, HttpStatus.OK);
		}else {
			map.clear();
			map.put("status", false);
			map.put("message", "Data is not found");
			return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@GetMapping("{courseId}")
	ResponseEntity<?> findById(@PathVariable int courseId) {
		Course course = this.service.findCourseById(courseId);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("data", course);
		map.put("success", true);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("{instructorId}")
	ResponseEntity<?> createCourse(@PathVariable int instructorId, @RequestBody Course course) {
		
		Instructor instructor = this.service.findInstructorById(instructorId);
		
		course.setInstructor(instructor);
		
		this.service.saveCourse(course);
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("data", course);
		map.put("success", true);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PutMapping("/{courseId}")
	ResponseEntity<?> updateInstructor(@PathVariable int courseId, @RequestBody Course course) {
		
		course.setId(courseId);
		
		this.service.saveCourse(course);
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("message", "Instructor updated!");
		map.put("success", true);
		map.put("data", course);
		
		return  new ResponseEntity<>(map, HttpStatus.CREATED);
		
	}
	
	
	
}
