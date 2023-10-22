package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductRestController {

	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<?> getProduct(){
		List<Product> listProduct = productService.getAllProducts();
		return ResponseEntity.ok(listProduct);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById (@PathVariable("id") Integer id) {
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}
	
	@PostMapping
	public ResponseEntity<?> saveProduct(@RequestBody Product productrq) {
		Product product = productService.createProduct(productrq);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id,@RequestBody Product productrq ){
		Product product = productService.updateProduct(id, productrq);
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id){
		productService.deleteProduct(id);
		return ResponseEntity.ok("Xoa thanh cong san pham: "+id);
	}
}
