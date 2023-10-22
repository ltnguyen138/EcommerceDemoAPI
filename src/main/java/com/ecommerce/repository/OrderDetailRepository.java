package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer>{
	
	@Query("SELECT o FROM OrderDetail o WHERE o.order.id = ?1")
	public List<Order> findAllOrderDTByOId(Integer orderId);
}
