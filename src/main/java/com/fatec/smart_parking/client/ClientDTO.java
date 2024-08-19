package com.fatec.smart_parking.client;

public record ClientDTO(
        Long id,
        String name,
        String email,
        String password
) {
}
