package com.example.demo.entities.dtos;

import jakarta.validation.constraints.NotNull;

public record OrderItemDto(
		@NotNull Integer quantity,
		@NotNull Long orderId,
		@NotNull Long productId) {

}
