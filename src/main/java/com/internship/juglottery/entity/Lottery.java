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

    @OneToMany(mappedBy = "lottery", fetch = FetchType.EAGER)
    List<Winner> winners;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public Lottery(Long id, String eventName, String city, LocalDate lotteryDate, Status status, List<Participant> participants, List<Voucher> vouchers, AppUser appUser) {
        this.id = id;
        this.eventName = eventName;
        this.city = city;
        this.lotteryDate = lotteryDate;
        this.status = status;
        this.participants = participants;
        this.vouchers = vouchers;
        this.appUser = appUser;
    }
}
