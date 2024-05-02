package com.example.demo.entities.enums;

public enum OrderStatus {

	WAITING_PAYMENT(1),
	PAID(2),
	PREPARING(3),
	SHIPPED(4),
	DELIVERED(5),
	CANCELED(6);
	
	private int code;
	
	private OrderStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static OrderStatus valueOf(int code) {
		for (OrderStatus value : OrderStatus.values()) {
			if(value.getCode() == code) return value;
		}
		throw new IllegalArgumentException("Invalid OrderStatus code!");
	}
}
