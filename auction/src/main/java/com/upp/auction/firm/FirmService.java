package com.upp.auction.firm;

import java.util.List;

public interface FirmService {

	public List<Firm> findAll();
	public Firm findOne(Long id);
	public Firm save(Firm firm);
	public Firm findByUserId(Long id);
	public void delete(Long id);
	
}
