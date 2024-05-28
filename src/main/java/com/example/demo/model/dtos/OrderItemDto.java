package com.example.demo.model.dtos;

import jakarta.validation.constraints.NotNull;

public record OrderItemDto(
		@NotNull Integer quantity,
		@NotNull Long orderId,
		@NotNull Long productId) {

}
