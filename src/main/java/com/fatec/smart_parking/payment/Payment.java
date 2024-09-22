package com.fatec.smart_parking.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fatec.smart_parking.parking_records.ParkingRecord;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parking_records_id", insertable = false, updatable = false)
    private ParkingRecord parkingRecord;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "pix_key", nullable = false)
    private String pixKey;

    @Column(name = "pix_code", nullable = false, columnDefinition = "TEXT")
    private String pixCode;




}
