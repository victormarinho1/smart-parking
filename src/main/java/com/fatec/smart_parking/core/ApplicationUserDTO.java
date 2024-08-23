package com.fatec.smart_parking.core;

public record ApplicationUserDTO(
    Long id,
    String email,
    String password,
    Role role
){

}