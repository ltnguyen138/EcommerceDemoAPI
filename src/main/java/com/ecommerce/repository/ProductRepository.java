package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	
	public Optional<Product>  findById(Integer id);
}
