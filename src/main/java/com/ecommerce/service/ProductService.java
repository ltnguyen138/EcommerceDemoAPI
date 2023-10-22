package com.ecommerce.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Product;
import com.ecommerce.exception.DuplicateRecordException;
import com.ecommerce.exception.NotFoundException;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		return (List<Product>) productRepository.findAll();
	}
	
	public	Product getProductById(Integer id) {
		return productRepository.findById(id).orElseThrow(()-> new NotFoundException("san pham khong ton tai"));
		}
	
	public Product createProduct(Product productrq) {
		
		for(Product product : getAllProducts()) {
			if(product.getName().equals(productrq.getName())) {
				throw new DuplicateRecordException("Ten san pham da ton tai");
			}
		}
		productrq.setCreate_at(new Date());
		return productRepository.save(productrq);
	}
	
	public Product updateProduct(Integer id,Product productrq) {
		
		Product productDb= getProductById(id);
	
		for(Product product : getAllProducts()) {
			if(product.getName().equals(productrq.getName())
					&& !product.getName().equals(productDb.getName())) {
				throw new DuplicateRecordException("Ten san pham da ton tai");
			}
		}
		
		productDb.setName(productrq.getName());
		productDb.setCategory(productrq.getCategory());
		productDb.setCost(productrq.getCost());
		productDb.setPrice(productrq.getPrice());
		productDb.setQuantity(productrq.getQuantity());
		productDb.setUpdate_at(new Date());
		
		return productRepository.save(productDb);
	}
	
	public void deleteProduct(Integer id) {
		Product product = getProductById(id);
		productRepository.delete(product);
	}
}
