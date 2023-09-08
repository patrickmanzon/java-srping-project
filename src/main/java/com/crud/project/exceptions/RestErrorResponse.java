package com.crud.project.exceptions;

public class RestErrorResponse {
	private int status;
	private String message;
	private long timestamps;
	
	public RestErrorResponse() {
		
	}

	public RestErrorResponse(int status, String message, long timestamps) {
		this.status = status;
		this.message = message;
		this.timestamps = timestamps;

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(long timestamps) {
		this.timestamps = timestamps;
	}
}
