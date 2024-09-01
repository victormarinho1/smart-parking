package com.fatec.smart_parking.client;

import jakarta.persistence.*;
import lombok.*;

import com.fatec.smart_parking.core.ApplicationUser;
import lombok.experimental.SuperBuilder;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Client extends ApplicationUser {

    @Column(unique = true, nullable = false)
    private String document;

}