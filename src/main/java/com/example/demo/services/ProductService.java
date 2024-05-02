package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

import jakarta.transaction.Transactional;


@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	@Transactional
	public void delete(Product product) {
		productRepository.delete(product);
	}

}
