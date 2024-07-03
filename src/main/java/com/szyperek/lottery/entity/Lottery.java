package com.szyperek.lottery.entity;

import com.szyperek.lottery.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lottery")
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "lottery_date")
    private LocalDate lotteryDate;

    @Column(name = "city", nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "lottery")
    @ToString.Exclude
    List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "lottery")
    @ToString.Exclude
    List<Voucher> vouchers = new ArrayList<>();

    @OneToMany(mappedBy = "lottery", fetch = FetchType.EAGER) // TODO: check if eager is needed
    List<Winner> winners = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Lottery lottery = (Lottery) o;
        return getId() != null && Objects.equals(getId(), lottery.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
