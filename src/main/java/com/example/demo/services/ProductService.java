package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.DatabaseException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.entities.Product;
import com.example.demo.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> productOpt = productRepository.findById(id);
		return productOpt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public void delete(Long id) {
		try {
			if (!productRepository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}
			productRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Product update(Long id, Product product) {
		try {
			Product entity = productRepository.getOne(id);
			updateData(entity, product);
			return productRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateData(Product entity, Product product) {
		entity.setCategory(product.getCategory());
		entity.setDescription(product.getDescription());
		entity.setImgUrl(product.getImgUrl());
		entity.setName(product.getName());
		entity.setPrice(product.getPrice());
	}
	
}
