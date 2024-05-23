package com.example.demo.resources;

import java.util.List;
import java.util.Optional;

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

import com.example.demo.entities.OrderItem;
import com.example.demo.entities.dtos.OrderItemDto;
import com.example.demo.services.OrderItemService;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;

import jakarta.validation.Valid;

/*findAll
 *save
 *findById
 *delete
 */

@RestController
@RequestMapping(value = "/orderItems")
public class OrderItemResource {
	
	@Autowired
	public OrderItemService oiService;
	
	@Autowired
	public OrderService orderService;
	
	@Autowired
	public ProductService productService;
	
	@PostMapping
	public ResponseEntity<OrderItem> save(@RequestBody @Valid OrderItemDto oiDto) {
		OrderItem oi = new OrderItem();
		oi.setOrder(orderService.findById(oiDto.orderId()).get());
		oi.setProduct(productService.findById(oiDto.productId()).get());
		oi.setQuantity(oiDto.quantity());
		oi.setPrice(oi.getSubTotal());
		return ResponseEntity.status(HttpStatus.CREATED).body(oiService.save(oi));
	}
	
	@GetMapping
	public ResponseEntity<List<OrderItem>> findAll() {
		List<OrderItem> list = oiService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<OrderItem> oiOpt = oiService.findById(id);
		if (oiOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OrderItem not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id).get());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value="id") Long id){
		Optional<OrderItem> oiOpt = oiService.findById(id);
		if (oiOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Item not found.");
		}
		oiService.delete(oiOpt.get());
		return ResponseEntity.status(HttpStatus.OK).body("Order Item deleted successfully.");
	}
	
	@PutMapping("/{id}")
	//TODO: update price on orderItem
	public ResponseEntity<Object> update(@PathVariable(value="id") Long id, @RequestBody @Valid OrderItemDto oiDto){
		Optional<OrderItem> oiOpt = oiService.findById(id);
		if(oiOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Item not found");
		}
		OrderItem oi = oiOpt.get();
		oi.setProduct(productService.findById(oiDto.productId()).get());
		oi.setQuantity(oiDto.quantity());
		
		return ResponseEntity.status(HttpStatus.OK).body(oiService.save(oi));
	}
	
}
