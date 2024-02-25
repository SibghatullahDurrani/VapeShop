package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "coupon_code"
    )
    private CouponCode couponCode;

    @NotNull
    @FutureOrPresent
    @Column(
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;

    @NotNull
    @FutureOrPresent
    private LocalDateTime lastModifiedAt;
}
