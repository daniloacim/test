package com.upp.auction.requests;

public class RegistrationFirmRequest {

	private Long category;
	private Integer distanceArea;
	private String taskId;
	private Long userId;

	public RegistrationFirmRequest() {
		super();
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Integer getDistanceArea() {
		return distanceArea;
	}

	public void setDistanceArea(Integer distanceArea) {
		this.distanceArea = distanceArea;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
