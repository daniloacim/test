package com.upp.auction.category;

import java.util.List;

public interface CategoryService {

	
	public Category findOne(Long id);
	public Category save(Category cat);
	public void delete(Long id);
	public List<Category> findAll();
}
