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

import com.example.demo.model.dtos.ClientDto;
import com.example.demo.model.dtos.OrderItemDto;
import com.example.demo.model.entities.Order;
import com.example.demo.model.entities.OrderItem;
import com.example.demo.model.entities.Payment;
import com.example.demo.services.ClientService;
import com.example.demo.services.OrderItemService;
import com.example.demo.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Ações de pedido")
public class OrderResource {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private OrderItemService oiService;

	@GetMapping
	@Operation(description = "Retorna todos os pedidos")
	public ResponseEntity<List<Order>> findAll() {
		List<Order> list = orderService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping(value = "/{id}")
	@Operation(description = "Retorna um pedido pelo ID")
	public ResponseEntity<Order> findById(@PathVariable Long id){
		Order order = orderService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(order);
	}
	
	@PostMapping
	@Operation(description = "Salva um pedido. Espera as informações do cliente")
	public ResponseEntity<Object> save(@RequestBody @Valid ClientDto clientDto) {
		Order order = new Order(Instant.now(), clientService.getOrCreateClientByPhone(clientDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
	}
	
	@PostMapping(value = "/addItem")
	@Operation(description = "Adiciona um item de pedido ao pedido")
	public ResponseEntity<OrderItem> addItems(@RequestBody @Valid OrderItemDto oiDto) {
		OrderItem oi = oiService.instantiateOrderItemByDto(oiDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(oiService.save(oi));
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(description = "Deleta um pedido")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "{orderId}/registerPayment")
	@Operation(description = "Registra o pagamento do pedido")
	public ResponseEntity<Payment> registerPayment(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.registerPayment(orderService.findById(orderId)));
	}
	
	@PutMapping(value = "{orderId}/updateStatus")
	@Operation(description = "Atualiza o status do pedido (PREPARING, SHIPPED, DELIVERED, PAID, CANCELED). Obs.: método não cancela pedido")
	public ResponseEntity<Order> updateStatus(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.updateStatus(orderId));
	}
	
	@PutMapping(value = "{orderId}/cancelOrder")
	@Operation(description = "Cancela o pedido")
	public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(orderId));
	}
	
}
