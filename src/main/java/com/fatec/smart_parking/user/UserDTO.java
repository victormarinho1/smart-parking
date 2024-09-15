package com.fatec.smart_parking.user;

import com.fatec.smart_parking.core.Role;

public record UserDTO(
        Long id,
        String name,
        String email,
        Role role,
        Boolean enabled
) {
}
