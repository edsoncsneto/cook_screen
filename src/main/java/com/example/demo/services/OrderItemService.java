package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.OrderItem;
import com.example.demo.entities.dtos.OrderItemDto;
import com.example.demo.repositories.OrderItemRepository;
import com.example.demo.services.exceptions.DatabaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderItemService {

	@Autowired
	public OrderItemRepository oiRepository;
	
	@Autowired
	public OrderService orderService;
	
	@Autowired
	public ProductService productService;
	
	public OrderItem save (OrderItem oi) {
		return oiRepository.save(oi);
	}
	
	public List<OrderItem> findAll() {
		return oiRepository.findAll();
	}
	
	public OrderItem findById(Long id){
		Optional<OrderItem> oiOpt = oiRepository.findById(id);
		return oiOpt.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public OrderItem update(Long id, OrderItem oi) {
		try {
			OrderItem entity = oiRepository.getOne(id);
			updateData(entity, oi);
			return oiRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateData(OrderItem entity, OrderItem oi) {
		entity.setProduct(oi.getProduct());
		entity.setQuantity(oi.getQuantity());
		entity.setSubTotal();;
	}
	
	public void delete(Long id) {
		try {
			if (!oiRepository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}
			oiRepository.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public OrderItem instantiateOrderItemByDto (OrderItemDto oiDto) {
		OrderItem oi = new OrderItem();
		oi.setOrder(orderService.findById(oiDto.orderId()));
		oi.setProduct(productService.findById(oiDto.productId()));
		oi.setQuantity(oiDto.quantity());
		oi.setSubTotal();
		return oi;
	}
	
}
