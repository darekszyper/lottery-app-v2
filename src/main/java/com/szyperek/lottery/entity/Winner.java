package com.szyperek.lottery.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
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
}
