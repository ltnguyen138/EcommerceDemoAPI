package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ecommerce.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{
	
	@Query("SELECT o FROM Order o WHERE o.user.id = ?1")
	public List<Order> findAllOrderByUId(Integer userId);
	
	public Optional<Order>  findById(Integer id);
}
