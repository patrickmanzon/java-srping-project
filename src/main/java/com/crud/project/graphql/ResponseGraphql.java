package com.crud.project.graphql;

import java.util.List;

public class ResponseGraphql {
	
	private String type;
	
	private List<?> data;
	
	private Integer perPage;
	
	private Integer total;
	
	private Integer lastPage;
	
	private Integer page;
	
	public ResponseGraphql() {
		
	}

//	public ResponseGraphql(String type,  List<?> data, Integer total) {
//		this.type = type;
//		this.data = data;
//		this.total = total;
//	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> objects) {
		this.data = objects;
	}
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPerPage() {
		return perPage;
	}

	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	
	
}
