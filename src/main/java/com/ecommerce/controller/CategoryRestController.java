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

import com.ecommerce.service.CategoryService;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryRestController {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<?> getCategory(){
		List<Category> listCategory = categoryService.listAllCategory();
		return ResponseEntity.ok(listCategory);
	}
	
	@PostMapping
	public ResponseEntity<?> saveCategory(@RequestBody Category categoryrq) {
		Category category = categoryService.createCategory(categoryrq);
		return ResponseEntity.status(HttpStatus.CREATED).body(category);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable("id") Integer id,@RequestBody Category categoryrq ){
		Category category = categoryService.updateCategory(id, categoryrq);
		return ResponseEntity.ok(category);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id){
		categoryService.deleteCategory(id);
		return ResponseEntity.ok("Xoa thanh cong loai san pham: "+id);
	}
}
