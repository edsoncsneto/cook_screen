package com.example.demo.model.dtos;

import com.example.demo.model.enums.ProductCategories;

import jakarta.validation.constraints.NotNull;

public record ProductDto(
		@NotNull String name,
		@NotNull String description,
		@NotNull Double price,
		@NotNull String imgUrl,
		@NotNull ProductCategories category) {

}
