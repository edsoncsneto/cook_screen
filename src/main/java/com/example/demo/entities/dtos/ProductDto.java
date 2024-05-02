package com.example.demo.entities.dtos;

import com.example.demo.entities.enums.ProductCategories;

import jakarta.validation.constraints.NotNull;

public record ProductDto(
		@NotNull String name,
		@NotNull String description,
		@NotNull Double price,
		@NotNull String imgUrl,
		@NotNull ProductCategories category) {

}
