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
@Table(name = "registration_token")
public class RegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Participant.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    public RegistrationToken(String token, Participant participant) {
        this.token = token;
        this.participant = participant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationToken that = (RegistrationToken) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(token, that.token)) return false;
        return Objects.equals(participant, that.participant);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (participant != null ? participant.hashCode() : 0);
        return result;
    }
}


