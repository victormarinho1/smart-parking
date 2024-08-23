package com.fatec.smart_parking.client;

import jakarta.persistence.*;
import lombok.*;

import com.fatec.smart_parking.core.ApplicationUser;


@Entity
@Getter
@Setter
@DiscriminatorValue("CLIENT")
public class Client extends ApplicationUser {

    @Column(name = "name", nullable = false)
    private String name;

    public Client(Long id,String name, String email,String password){
        super(id, email, password);
        this.name = name;
    }
}