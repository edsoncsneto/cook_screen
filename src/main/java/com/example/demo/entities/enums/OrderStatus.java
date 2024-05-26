package com.example.demo.entities.enums;

public enum OrderStatus {

	PREPARING,
	SHIPPED,
	DELIVERED,
	PAID,
	CANCELED;
	
	//Is not possible to advande from paid to canceled
	public OrderStatus nextStatus() {
		if (this == PAID || this == CANCELED) {
			return this;
		}
		return OrderStatus.values()[this.ordinal()+1];	
	}
	
	public OrderStatus cancelOrder() {
		return OrderStatus.CANCELED;
	}
	
}
