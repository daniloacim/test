package com.upp.auction.user;

import java.util.List;

public interface UserService {
	
	public List<User> findAll();
	
	public User save(User user);
	
	public User findOne(Long id);
	
	public void delete(Long id);
	
	public User findOneByUsername(String username);
	
	public User findOneByConfirmationCode(String confirmationMail);

}
