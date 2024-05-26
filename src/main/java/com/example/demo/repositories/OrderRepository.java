package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.totalPrice = (SELECT SUM(oi.price) FROM OrderItem oi WHERE oi.order.id = o.id) WHERE o.id = :orderId")
    void updateOrderTotalPrice(@Param("orderId") Long orderId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Payment p WHERE p.order.id = :orderId")
    boolean existsPaymentByOrderId(@Param("orderId") Long orderId);
}
