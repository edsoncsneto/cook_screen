package com.example.demo.resources;

import java.time.Instant;
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

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderItem;
import com.example.demo.entities.Payment;
import com.example.demo.entities.dtos.ClientDto;
import com.example.demo.entities.dtos.OrderItemDto;
import com.example.demo.services.ClientService;
import com.example.demo.services.OrderItemService;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;

import jakarta.validation.Valid;

/*findAll
 *findById
 *save: receives a ClientDTO
 *addItem: receives a OrderItemDto (quantity, orderId, productId)
 *delete
 *registerPayment
 *cancelOrder
 *updateOrder
 */

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderItemService oiService;

	@GetMapping
	public ResponseEntity<List<Order>> findAll() {
		List<Order> list = orderService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id){
		Order order = orderService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(order);
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid ClientDto clientDto) {
		Order order = new Order(Instant.now(), clientService.getOrCreateClientByPhone(clientDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
	}
	
	@PostMapping(value = "/addItem")
	public ResponseEntity<OrderItem> addItems(@RequestBody @Valid OrderItemDto oiDto) {
		OrderItem oi = oiService.instantiateOrderItemByDto(oiDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(oiService.save(oi));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "{orderId}/registerPayment")
	public ResponseEntity<Payment> registerPayment(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.registerPayment(orderService.findById(orderId)));
	}
	
	@PutMapping(value = "{orderId}/updateStatus")
	public ResponseEntity<Order> updateStatus(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.updateStatus(orderId));
	}
	
	@PutMapping(value = "{orderId}/cancelOrder")
	public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(orderId));
	}
	
}
