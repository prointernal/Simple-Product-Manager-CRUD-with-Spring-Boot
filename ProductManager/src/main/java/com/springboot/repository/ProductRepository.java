package com.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	
	@Query("SELECT p From Product p WHERE "
			+ "CONCAT(p.id,p.name,p.brand,p.madein,p.price)"
			+ "LIKE %?1%")
	public Page<Product> findAll(String keyword, Pageable pageable);
}
 