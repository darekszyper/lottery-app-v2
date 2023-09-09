package com.internship.juglottery.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}


