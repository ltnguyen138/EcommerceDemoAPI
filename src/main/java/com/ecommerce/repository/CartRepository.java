package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;

import jakarta.transaction.Transactional;

@Transactional
public interface CartRepository extends CrudRepository<Cart, Integer>{

	public List<Cart>  findByUser(User user);
	public Optional<Cart> findByUserAndProduct(User user, Product product);
	
	
	@Modifying
	@Query("UPDATE Cart c SET c.quantiny = :quantity WHERE c.user.id = :userId AND c.product.id= :productId")
	public void updateQuantity(@Param("quantity") Integer quantity,@Param("userId") Integer userId,
			@Param("productId") Integer productId);
	@Modifying
	@Query("DELETE FROM Cart c  WHERE c.user.id = :userId AND c.product.id= :productId")
	public void dedeleteByCustomerAndProduct(@Param("userId") Integer userId, @Param("productId") Integer productId);

	@Modifying
	@Query("DELETE FROM Cart c  WHERE c.user.id = :userId ")
	public void dedeleteByCustomer(@Param("userId") Integer userId);
	
}
