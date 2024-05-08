package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.OrderItem;
import com.example.demo.repositories.OrderItemRepository;

@Service
public class OrderItemService {

	@Autowired
	public OrderItemRepository oiRepository;
	
	public OrderItem save (OrderItem oi) {
		return oiRepository.save(oi);
	}
	
	public List<OrderItem> findAll() {
		return oiRepository.findAll();
	}
	
	public Optional<OrderItem> findById(Long id){
		return oiRepository.findById(id);
	}
}
