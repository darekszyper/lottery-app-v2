package com.szyperek.lottery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "winner")
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @OneToOne
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    @JoinColumn(name = "lottery_id", nullable = false)
    @ManyToOne
    private Lottery lottery;

    public Winner(Participant participant, Voucher voucher, Lottery lottery) {
        this.participant = participant;
        this.voucher = voucher;
        this.lottery = lottery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Winner winner = (Winner) o;
        return getId() != null && Objects.equals(getId(), winner.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
