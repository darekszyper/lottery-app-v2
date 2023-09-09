package com.internship.juglottery.entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "is_email_confirmed")
    private boolean isEmailConfirmed;

    @JoinColumn(name = "lottery_id")
    @ManyToOne
    private Lottery lottery;

    public Participant(String firstName, String email) {
        this.firstName = firstName;
        this.email = email;
    }
}
