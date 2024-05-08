package com.example.demo.entities.enums;

public enum OrderStatus {

	WAITING_PAYMENT,
	PAID,
	PREPARING,
	SHIPPED,
	DELIVERED,
	CANCELED;
	
	private OrderStatus nextStatus() {
		//Is not possible to advande from delivered to canceled
		if (this.ordinal() < OrderStatus.values().length-2) {
			return OrderStatus.values()[this.ordinal()+1];
		} else {
			return this;
		}
	}
	
	private OrderStatus cancelOrder() {
		return OrderStatus.CANCELED;
	}
	
}
