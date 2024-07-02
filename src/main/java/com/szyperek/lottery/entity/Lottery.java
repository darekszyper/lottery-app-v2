package com.szyperek.lottery.entity;

import com.szyperek.lottery.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lottery lottery = (Lottery) o;

        if (!Objects.equals(id, lottery.id)) return false;
        if (!Objects.equals(eventName, lottery.eventName)) return false;
        if (!Objects.equals(city, lottery.city)) return false;
        if (!Objects.equals(lotteryDate, lottery.lotteryDate)) return false;
        if (status != lottery.status) return false;
        if (!Objects.equals(participants, lottery.participants))
            return false;
        if (!Objects.equals(vouchers, lottery.vouchers)) return false;
        if (!Objects.equals(winners, lottery.winners)) return false;
        return Objects.equals(appUser, lottery.appUser);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (lotteryDate != null ? lotteryDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (participants != null ? participants.hashCode() : 0);
        result = 31 * result + (vouchers != null ? vouchers.hashCode() : 0);
        result = 31 * result + (winners != null ? winners.hashCode() : 0);
        result = 31 * result + (appUser != null ? appUser.hashCode() : 0);
        return result;
    }
}
