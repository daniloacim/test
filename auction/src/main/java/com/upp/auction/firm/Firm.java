package com.upp.auction.firm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.upp.auction.category.Category;
import com.upp.auction.order.OrderS;
import com.upp.auction.user.User;

@Entity
public class Firm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@ManyToOne
	private Category category;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "firm_order", joinColumns = @JoinColumn(name = "firm_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))

	private List<OrderS> orders;

	private Integer distanceArea;

	@ElementCollection
	private List<Integer> ranks;

	private Double avgRank;

	public Firm() {
		super();
		this.orders = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getDistanceArea() {
		return distanceArea;
	}

	public void setDistanceArea(Integer distanceArea) {
		this.distanceArea = distanceArea;
	}

	public List<Integer> getRanks() {
		return ranks;
	}

	public void setRanks(List<Integer> ranks) {
		this.ranks = ranks;
	}

	public Double getAvgRank() {
		Double sum = (double) 0;
		avgRank = (double) 0;
		for (int i = 0; i < this.ranks.size(); i++) {
			sum += ranks.get(i);

		}
		avgRank = sum / this.ranks.size();
		return avgRank;

	}

	public void setAvgRank(Double avgRank) {
		this.avgRank = avgRank;
	}

	public List<OrderS> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderS> orders) {
		this.orders = orders;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
