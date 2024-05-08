package com.example.demo.resources;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Client;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderItem;
import com.example.demo.entities.dtos.ClientDto;
import com.example.demo.entities.dtos.OrderItemDto;
import com.example.demo.services.ClientService;
import com.example.demo.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientResource clientResource;
	
	@Autowired
	private OrderItemResource oiResource;

	@GetMapping
	public ResponseEntity<List<Order>> findAll() {
		List<Order> list = orderService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id){
		Optional<Order> orderOpt = orderService.findById(id);
		if (orderOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id).get());
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid ClientDto clientDto) {
		Optional<Client> clientOpt = clientService.findByPhone(clientDto.phone());
		Client client = new Client();
		
		if (clientOpt.isPresent()) {
			client = clientOpt.get();
			
		} else {
			client = clientResource.save(clientDto).getBody();
		}
		Order order = new Order(Instant.now(), client);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
	}
	
	@PostMapping(value = "/addItem")
	public ResponseEntity<OrderItem> addItems(@RequestBody @Valid OrderItemDto oiDto) {
		ResponseEntity<OrderItem> res = oiResource.save(oiDto);
		orderService.updateOrderTotalPrice(oiDto.orderId());
		return res;
	}
	
}
