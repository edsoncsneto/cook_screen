package com.example.demo.entities.enums;

public enum ProductCategories {

	DRINK(1),
	STARTER(2),
	MAIN_COUSE(3),
	DESSERT(4);
	
	private int code;
	
	private ProductCategories(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static ProductCategories valueOf(int code) {
		for (ProductCategories value : ProductCategories.values()) {
			if (value.getCode() == code) return value;
		}
		throw new IllegalArgumentException("Invalid ProductCategories code!");
	}
}
