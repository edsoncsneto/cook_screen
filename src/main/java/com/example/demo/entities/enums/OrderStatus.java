package com.example.demo.entities.enums;

public enum OrderStatus {

	PREPARING,
	SHIPPED,
	DELIVERED,
	PAID,
	CANCELED;
	
	public OrderStatus nextStatus() {
		//Is not possible to advande from delivered to canceled
		if (this.ordinal() < OrderStatus.values().length-2) {
			return OrderStatus.values()[this.ordinal()+1];
		} else {
			return this;
		}
	}
	
	public OrderStatus cancelOrder() {
		return OrderStatus.CANCELED;
	}
	
}
