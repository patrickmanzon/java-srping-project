package com.crud.project.graphql;



public class FilterCommand {
	
	private Integer id;
	
	private String type;
	
	private String keyword;
	
	private Integer page;
	
	private Integer perPage = 10;
	
	public Integer getPage() {
		return page;
	}



	public FilterCommand() {
		
	}



	public FilterCommand(Integer id, String type, String keyword, Integer page, Integer perPage) {

		this.id = id;
		this.type = type;
		this.keyword = keyword;
		this.page = page;
		this.perPage = perPage;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPerPage() {
		return perPage;
	}

	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}
	
	

}
