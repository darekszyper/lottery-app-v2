package com.szyperek.lottery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Voucher voucher = (Voucher) o;

        if (!Objects.equals(id, voucher.id)) return false;
        if (!Objects.equals(voucherName, voucher.voucherName)) return false;
        if (!Objects.equals(activationCode, voucher.activationCode))
            return false;
        if (!Objects.equals(expirationDate, voucher.expirationDate))
            return false;
        if (!Objects.equals(lottery, voucher.lottery)) return false;
        return Objects.equals(appUser, voucher.appUser);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (voucherName != null ? voucherName.hashCode() : 0);
        result = 31 * result + (activationCode != null ? activationCode.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (lottery != null ? lottery.hashCode() : 0);
        result = 31 * result + (appUser != null ? appUser.hashCode() : 0);
        return result;
    }
}