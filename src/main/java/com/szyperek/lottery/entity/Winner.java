package com.szyperek.lottery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "winner")
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @OneToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @JoinColumn(name = "lottery_id")
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
        if (o == null || getClass() != o.getClass()) return false;

        Winner winner = (Winner) o;

        if (!Objects.equals(id, winner.id)) return false;
        if (!Objects.equals(participant, winner.participant)) return false;
        if (!Objects.equals(voucher, winner.voucher)) return false;
        return Objects.equals(lottery, winner.lottery);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (participant != null ? participant.hashCode() : 0);
        result = 31 * result + (voucher != null ? voucher.hashCode() : 0);
        result = 31 * result + (lottery != null ? lottery.hashCode() : 0);
        return result;
    }
}
