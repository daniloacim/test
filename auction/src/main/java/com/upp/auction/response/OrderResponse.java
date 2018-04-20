package com.upp.auction.response;

import java.util.Date;

public class OrderResponse {

	private Long id;

	private Long categoryId;

	private String description;

	private Long estimatedValue;

	private Date offersDeadline;

	private Long offersLimit;

	private Date serviceDeadline;

	private String taskName;
	private String firstName;
	private String lastName;
	private Long rang;
	
	private String additionalInfoRequest;
	public OrderResponse() {
		super();
	}

	public OrderResponse(Long id, Long categoryId, String description, Long estimatedValue, Date offersDeadline,
			Long offersLimit, Date serviceDeadline,String taskName,String additionalInfoRequest,Long rang) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.description = description;
		this.estimatedValue = estimatedValue;
		this.offersDeadline = offersDeadline;
		this.offersLimit = offersLimit;
		this.serviceDeadline = serviceDeadline;
		this.taskName = taskName;
		this.additionalInfoRequest = additionalInfoRequest;
		this.rang = rang;
	}

	
	
	public OrderResponse(Long id, Long categoryId, String description, Long estimatedValue, Date offersDeadline,
			Long offersLimit, Date serviceDeadline, String firstName, String lastName,String taskName,String additionalInfoRequest,Long rang) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.description = description;
		this.estimatedValue = estimatedValue;
		this.offersDeadline = offersDeadline;
		this.offersLimit = offersLimit;
		this.serviceDeadline = serviceDeadline;
		this.firstName = firstName;
		this.lastName = lastName;
		this.taskName = taskName;
		this.additionalInfoRequest  = additionalInfoRequest;
		this.rang = rang;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getEstimatedValue() {
		return estimatedValue;
	}

	public void setEstimatedValue(Long estimatedValue) {
		this.estimatedValue = estimatedValue;
	}

	public Date getOffersDeadline() {
		return offersDeadline;
	}

	public void setOffersDeadline(Date offersDeadline) {
		this.offersDeadline = offersDeadline;
	}

	public Long getOffersLimit() {
		return offersLimit;
	}

	public void setOffersLimit(Long offersLimit) {
		this.offersLimit = offersLimit;
	}

	public Date getServiceDeadline() {
		return serviceDeadline;
	}

	public void setServiceDeadline(Date serviceDeadline) {
		this.serviceDeadline = serviceDeadline;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getAdditionalInfoRequest() {
		return additionalInfoRequest;
	}

	public void setAdditionalInfoRequest(String additionalInfoRequest) {
		this.additionalInfoRequest = additionalInfoRequest;
	}

	public Long getRang() {
		return rang;
	}

	public void setRang(Long rang) {
		this.rang = rang;
	}
	
	
	
}
