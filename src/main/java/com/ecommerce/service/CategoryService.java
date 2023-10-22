package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Category;
import com.ecommerce.exception.DuplicateRecordException;
import com.ecommerce.exception.NotFoundException;
import com.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> listAllCategory() {
		return (List<Category>) categoryRepository.findAll();
	}
	
	public Category getCategoryById(Integer id) {
		return categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("khong tim thay danh muc co id: "+id));
		
	}
	
	public Category createCategory(Category categoryrq) {
		
		for(Category category : listAllCategory()) {
			if(category.getName().equals(categoryrq.getName())) {
				throw new DuplicateRecordException("Ten loai hang hoa da ton tai");
			}
		}
		return categoryRepository.save(categoryrq);
	}
	
	public Category updateCategory(Integer id,Category categoryrq) {
		
		Category categoryDb = getCategoryById(id);
		
		for(Category category : listAllCategory()) {
			if(category.getName().equals(categoryrq.getName())
					&& !category.getName().equals(categoryDb.getName())) {
				throw new DuplicateRecordException("Ten loai hang hoa da ton tai");
			}
		}
		categoryDb.setName(categoryrq.getName());
		return categoryRepository.save(categoryDb);
	}
	
	public void deleteCategory(Integer id) {
		Category category = getCategoryById(id);
		categoryRepository.delete(category);
	}
}
