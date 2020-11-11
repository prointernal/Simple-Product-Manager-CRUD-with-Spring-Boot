package com.springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.model.Product;
import com.springboot.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	
	public Page<Product> listAll(int pageNumber , String sortField, String sortDir,
			String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNumber -1, 3,sort);
		if(keyword != null) {
			return productRepository.findAll(keyword,pageable);
		}
	
		return productRepository.findAll(pageable);
	}

	
	public void save(Product product) {
		productRepository.save(product);
	}
	
	public Product get(Long id) {
		return productRepository.findById(id).get();
	}
	
	public void delete(Long id) {
		productRepository.deleteById(id);
	}
	
	
	
}
