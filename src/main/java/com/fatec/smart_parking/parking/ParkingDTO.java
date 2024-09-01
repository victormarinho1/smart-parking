package com.fatec.smart_parking.parking;

import com.fatec.smart_parking.core.Role;

public record ParkingDTO(
        Long id,
        String name,
        String email,
        Role role,
        Boolean enabled
) {
}