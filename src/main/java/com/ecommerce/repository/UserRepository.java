package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	public Optional<User>  findByPhoneNumber(String phoneNumber);
	public Optional<User>  findById(Integer id);
}
