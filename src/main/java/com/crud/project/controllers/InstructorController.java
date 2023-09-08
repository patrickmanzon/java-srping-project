package com.crud.project.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.project.entity.Instructor;
import com.crud.project.interfaces.AppDAO;
import com.crud.project.interfaces.AppService;


@RestController
@RequestMapping("/api")
public class InstructorController {

	private AppService service;
	
	@Autowired
	InstructorController(AppService service) {
		this.service = service;
	}
	
	@GetMapping("/instructors")
	ResponseEntity<?> getAll() {
		
		List<Instructor> instructors = this.service.getAllInstructors();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		if(!instructors.isEmpty()) {
			map.put("success", true);
			map.put("data", instructors);
			
			return new ResponseEntity<>(map, HttpStatus.OK);
		}else {
			map.clear();
			map.put("status", false);
			map.put("message", "Data is not found");
			return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
	@GetMapping("/instructors/{instructorId}")
	ResponseEntity<?> getById(@PathVariable int instructorId) {
		
		Instructor instructor = this.service.findInstructorById(instructorId);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("data", instructor);
		map.put("success", true);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
		
	}
	
	@PostMapping("/instructors")
	ResponseEntity<?> saveInstructor(@RequestBody Instructor instructor) {
		
		instructor.setId(0);
		this.service.saveInstructor(instructor);
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("message", "Instructor created!");
		map.put("data", instructor);
		map.put("success", true);
		
		return  new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@PutMapping("/instructors/{instructorId}")
	ResponseEntity<?> updateInstructor(@PathVariable int instructorId, @RequestBody Instructor instructor) {
		
		instructor.setId(instructorId);
		
		this.service.saveInstructor(instructor);
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("message", "Instructor updated!");
		map.put("success", true);
		map.put("data", instructor);
		
		return  new ResponseEntity<>(map, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/instructors/{instructorId}")
	ResponseEntity<?> delerteInstructor(@PathVariable int instructorId) {
		
		this.service.deleteInstructorById(instructorId);
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("message", "Instructor deleted!");
		map.put("data", null);
		map.put("success", true);
		
		return  new ResponseEntity<>(map, HttpStatus.CREATED);
		
	}
	
	
}
