package com.upp.auction.firm;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FirmServiceImpl implements FirmService {

	@Autowired
	private FirmRepository repository;

	@Override
	public List<Firm> findAll() {
		// TODO Auto-generated method stub
		return (List<Firm>) repository.findAll();
	}

	@Override
	public Firm findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Firm save(Firm firm) {
		// TODO Auto-generated method stub
		return repository.save(firm);
	}

	@Override
	public Firm findByUserId(Long id) {
		// TODO Auto-generated method stub
		return repository.findByUserId(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}
}
