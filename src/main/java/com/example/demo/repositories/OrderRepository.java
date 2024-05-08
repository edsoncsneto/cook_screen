package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Modifying
	@Query("UPDATE tb_order SET total_price = (SELECT SUM(oi.price) FROM tb_order_item oi WHERE oi.order_id = tb_order.id) WHERE id = ?1;")
	default void updateOrderTotalPrice(Long order_id) {}
	
}
