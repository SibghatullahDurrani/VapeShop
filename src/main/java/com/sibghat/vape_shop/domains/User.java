package com.sibghat.vape_shop.domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_seq", allocationSize = 1)
    @Column(
            columnDefinition = "BIGINT"
    )
    private Long id;

    @NotBlank
    @Column(
            nullable = false
    )
    private String firstName;

    @NotBlank
    @Column(
            nullable = false
    )
    private String lastName;

    @NotBlank(message = "username must not be empty")
    @Column(
            unique = true,
            nullable = false,
            updatable = false
    )
    private String username;

    @Email
    @NotBlank(message = "email must not be empty")
    @Column(
            unique = true,
            nullable = false,
            updatable = false
    )
    private String email;

    @NotBlank(message = "password must not be empty")
    @Column(
            nullable = false
    )
    private String password;

    @NotBlank(message = "contact number must not be empty")
    @Column(
            unique = true,
            nullable = false,
            updatable = false
    )
    private String contactNumber;

    @NotBlank
    @Builder.Default
    @Column(
            nullable = false
    )
    private String role="ROLE_CLIENT";

    @Column(
            unique = true
    )
    private String verificationCode;

    private Long verificationCodeValidTill;

    @Builder.Default
    @Column(
            nullable = false
    )
    private boolean enabled=false;

    @NotNull
    @Builder.Default
    @Column(
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Karachi"));

    private LocalDateTime lastModifiedAt;

    @Column(
            nullable = false,
            updatable = false
    )
    private String createdBy;

    private String lastModifiedBy;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    @ToString.Exclude
    private List<Address> addresses;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private CouponCode couponCode;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Cart cart;

    @OneToMany(
            mappedBy = "user"
    )
    private List<Order> orders;

}
