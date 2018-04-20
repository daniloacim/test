package com.upp.auction.order;

import java.util.Date;
import java.util.List;

public interface OrderService {

	public List<OrderS> findAll();
	public OrderS findOne(Long id);
	public OrderS save(Long category,String description,Long estimatedValue,Date offersDeadline,Long offersLimit,Date serviceDeadline,String instanceId);
	public void delete(Long id);
	
}
