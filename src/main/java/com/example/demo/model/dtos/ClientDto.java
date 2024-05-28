package com.example.demo.model.dtos;

import jakarta.validation.constraints.NotNull;

public record ClientDto(
		@NotNull String name,
		@NotNull String phone) {
}
