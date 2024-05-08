package com.example.demo.entities.dtos;

import jakarta.validation.constraints.NotNull;

public record ClientDto(
		@NotNull String name,
		@NotNull String phone) {
}
