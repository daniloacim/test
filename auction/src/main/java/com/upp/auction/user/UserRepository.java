package com.upp.auction.user;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	public User findOneByUsername(String username);

	public User findOneByConfirmationCode(String confirmationCode);
}
