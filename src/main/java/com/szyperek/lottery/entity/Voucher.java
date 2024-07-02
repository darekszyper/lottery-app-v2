package com.szyperek.lottery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "voucher_name")
    private String voucherName;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "lottery_id")
    private Lottery lottery;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public Voucher(String activationCode, LocalDate expirationDate) {
        this.activationCode = activationCode;
        this.expirationDate = expirationDate;
    }
}