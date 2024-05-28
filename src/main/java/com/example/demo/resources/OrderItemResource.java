package com.example.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dtos.OrderItemDto;
import com.example.demo.model.entities.OrderItem;
import com.example.demo.services.OrderItemService;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;

import jakarta.validation.Valid;

/*findAll
 *save
 *findById
 *delete
 *update
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
	
	@GetMapping
	public ResponseEntity<List<OrderItem>> findAll() {
		List<OrderItem> list = oiService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		OrderItem oi = oiService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(oi);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value="id") Long id){
		oiService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable(value="id") Long id, @RequestBody @Valid OrderItemDto oiDto){
		OrderItem oi = oiService.instantiateOrderItemByDto(oiDto);
		return ResponseEntity.status(HttpStatus.OK).body(oiService.update(id, oi));
	}
	
}
