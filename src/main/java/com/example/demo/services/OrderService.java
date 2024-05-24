package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Order;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.services.exceptions.DatabaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> findAll(){
		return orderRepository.findAll();
	}
	
	public Order findById(Long id) {
		Optional<Order> orderOpt = orderRepository.findById(id);
		return orderOpt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Order save(Order order) {
		return orderRepository.save(order);
	}
	
	public void updateOrderTotalPrice(Long id) {
		orderRepository.updateOrderTotalPrice(id);
	}

	public void delete(Long id) {
		try {
			if (!orderRepository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}
			orderRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Order updateStatus(Long id) {
		try {
			Order entity = orderRepository.getOne(id);
			entity.getOrderStatus().nextStatus();
			return orderRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public Order cancelOrder(Long id) {
		try {
			Order entity = orderRepository.getOne(id);
			entity.getOrderStatus().cancelOrder();
			return orderRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
}
