package com.crud.project.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Course {
	
	private int id;
	
	private String title;

	@JsonIgnore
	private Instructor instructor;
	
	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Course() {
		
	}
	
	public Course(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
