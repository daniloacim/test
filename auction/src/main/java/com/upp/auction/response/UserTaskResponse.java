package com.upp.auction.response;

import java.util.Date;

public class UserTaskResponse {

	String taskName;
	String orderDescription;
	Integer offersNum;
	Long requestedOffersNum;
	Date serviceDeadline;
	Date offersDeadline;
	String additionalInfo;
	
	String firmAdditionalInfo;

	public UserTaskResponse() {
		super();
	}
	
	
	public UserTaskResponse(String taskName, String orderDescription, Integer offersNum, Long requestedOffersNum,
			Date serviceDeadline, Date offersDeadline) {
		super();
		this.taskName = taskName;
		this.orderDescription = orderDescription;
		this.offersNum = offersNum;
		this.requestedOffersNum = requestedOffersNum;
		this.serviceDeadline = serviceDeadline;
		this.offersDeadline = offersDeadline;
	}


	public UserTaskResponse(String taskName, String orderDescription, Integer offersNum, Long requestedOffersNum,
			Date serviceDeadline, Date offersDeadline,String additionalInfo) {
		super();
		this.taskName = taskName;
		this.orderDescription = orderDescription;
		this.offersNum = offersNum;
		this.requestedOffersNum = requestedOffersNum;
		this.serviceDeadline = serviceDeadline;
		this.offersDeadline = offersDeadline;
		this.additionalInfo = additionalInfo;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getOrderDescription() {
		return orderDescription;
	}
	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}
	public Integer getOffersNum() {
		return offersNum;
	}
	public void setOffersNum(Integer offersNum) {
		this.offersNum = offersNum;
	}
	public Long getRequestedOffersNum() {
		return requestedOffersNum;
	}
	public void setRequestedOffersNum(Long requestedOffersNum) {
		this.requestedOffersNum = requestedOffersNum;
	}
	public Date getServiceDeadline() {
		return serviceDeadline;
	}
	public void setServiceDeadline(Date serviceDeadline) {
		this.serviceDeadline = serviceDeadline;
	}
	public Date getOffersDeadline() {
		return offersDeadline;
	}
	public void setOffersDeadline(Date offersDeadline) {
		this.offersDeadline = offersDeadline;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}


	public String getFirmAdditionalInfo() {
		return firmAdditionalInfo;
	}

	public void setFirmAdditionalInfo(String firmAdditionalInfo) {
		this.firmAdditionalInfo = firmAdditionalInfo;
	}
	
	
}
