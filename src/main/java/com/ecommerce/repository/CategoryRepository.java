package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
	public Optional<Category> findById(Integer id);
	
}
