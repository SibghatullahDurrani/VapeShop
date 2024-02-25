package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
    private BigInteger id;

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
    private String password;

    @NotBlank(message = "contact number must not be empty")
    @Column(
            unique = true
    )
    private String contactNumber;

    @NotBlank
    private String role;

    @Column(
            unique = true
    )
    private Integer verificationCode;

    private boolean enabled;

    @NotNull
    @FutureOrPresent
    @Column(
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;

    @NotNull
    @FutureOrPresent
    @Column(
            nullable = false
    )
    private LocalDateTime lastModifiedAt;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
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
