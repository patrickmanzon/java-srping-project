package com.crud.project.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.project.entity.Course;
import com.crud.project.entity.Instructor;
import com.crud.project.graphql.FilterCommand;
import com.crud.project.graphql.PaginateResult;
import com.crud.project.graphql.ResponseGraphql;
import com.crud.project.interfaces.AppService;

@RestController
public class GraphqlController {
	
    private AppService appService;
	
	GraphqlController(AppService appService) {
		this.appService = appService;
	}
	
	@QueryMapping
	public Map<String, Object> instructorById(@Argument int id) {
		Instructor instructor = this.appService.findInstructorById(id);
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("isntructor", instructor);
		
		return map;
	}
	
	@QueryMapping
	public PaginateResult getGroupCommandInstructor(@Argument FilterCommand filter) {

		PaginateResult result = this.appService.getInstructorsWithFilter(filter);
		
		return result;
	}
	
	@QueryMapping
	public List<Instructor> allInstructor() {
		List<Instructor> instructors = this.appService.getAllInstructors();
		
		return instructors;
	}
	
	
	@MutationMapping
	public Instructor addInstructor(@Argument String firstName, @Argument String lastName, @Argument String email)
	{
		Instructor instructor = new Instructor(firstName, lastName, email);
			
		this.appService.saveInstructor(instructor);
		
		return instructor;
		
	}
	
	@QueryMapping
	public Course courseById(@Argument int id) {
		Course course = this.appService.findCourseById(id);
		
		return course;
	}

}
