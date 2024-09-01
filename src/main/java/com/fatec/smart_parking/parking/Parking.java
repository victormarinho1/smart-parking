package com.fatec.smart_parking.parking;

import com.fatec.smart_parking.core.ApplicationUser;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Parking extends ApplicationUser {
}
