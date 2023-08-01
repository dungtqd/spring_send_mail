package com.dungtq.spring_mail_sender.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Getter
@Setter
//@ApiModel(value = "User model")
//@EqualsAndHashCode(callSuper = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "enable")
    private boolean enable;
}
