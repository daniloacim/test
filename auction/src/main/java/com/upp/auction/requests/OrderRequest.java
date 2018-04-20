package com.upp.auction.requests;

import java.util.Date;

public class OrderRequest {
	
	private Long category;
	
	private String description;
	
	private Long estimatedValue;
	
	private Date offersDeadline;
	
	private Long offersLimit;
	
	private Date serviceDeadline;
	
	public OrderRequest() {
		super();
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
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
	
	
	
}
