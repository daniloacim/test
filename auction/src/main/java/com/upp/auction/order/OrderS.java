package com.upp.auction.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upp.auction.category.Category;
import com.upp.auction.firm.Firm;
import com.upp.auction.user.User;

@Entity
public class OrderS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Category category;

	private String description;

	private Long estimatedValue;

	private Date offersDeadline;

	private Long offersLimit;

	private Date serviceDeadline;

	@JsonIgnore
	@ManyToMany(mappedBy = "orders")
	private List<Firm> firms;

	@ManyToOne
	private User user;
	public OrderS(Category category2, String description2, Long estimatedValue2, Date offersDeadline2,
			Long offersLimit2, Date serviceDeadline2) {
		this.category = category2;
		this.description = description2;
		this.estimatedValue = estimatedValue2;
		this.offersDeadline = offersDeadline2;
		this.offersLimit = offersLimit2;
		this.serviceDeadline = serviceDeadline2;
	}

	public OrderS() {
		super();
		this.firms = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
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

	public List<Firm> getFirms() {
		return firms;
	}

	public void setFirms(List<Firm> firms) {
		this.firms = firms;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
