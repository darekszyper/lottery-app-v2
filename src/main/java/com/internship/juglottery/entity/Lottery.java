package com.internship.juglottery.entity;

import com.internship.juglottery.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lottery")
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "city")
    private String city;

    @Column(name = "lottery_date")
    private LocalDate lotteryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "lottery")
    List<Participant> participants;

    @OneToMany(mappedBy = "lottery")
    List<Voucher> vouchers;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public Lottery(String eventName, LocalDate lotteryDate, List<Voucher> vouchers, Status status, List<Participant> participants) {
        this.eventName = eventName;
        this.lotteryDate = lotteryDate;
        this.status = status;
        this.participants = participants;
        this.vouchers = vouchers;
    }
}
