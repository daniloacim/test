package com.upp.auction.firm;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FirmRepository extends CrudRepository<Firm, Long>{

	public Firm findByUserId(Long id);

	public List<Firm> findByCategoryId(Long category);
}
