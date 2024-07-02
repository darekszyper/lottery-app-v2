package com.szyperek.lottery.entity;

import com.szyperek.lottery.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PasswordResetToken passwordResetToken;
}
