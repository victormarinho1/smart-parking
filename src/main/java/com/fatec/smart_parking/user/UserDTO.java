package com.fatec.smart_parking.client;

import com.fatec.smart_parking.core.Role;

public record ClientDTO(
        Long id,
        String name,
        String document,
        String email,
        Role role,
        Boolean enabled
) {
}
