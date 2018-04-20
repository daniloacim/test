package com.upp.auction.response;

import java.util.Map;

public class RegistrationResponse {

	private String status;
	private Long userId;
	Map<String, Object> taskMap;

	public RegistrationResponse() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getTaskMap() {
		return taskMap;
	}

	public void setTaskMap(Map<String, Object> taskMap) {
		this.taskMap = taskMap;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
