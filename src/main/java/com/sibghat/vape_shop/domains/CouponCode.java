package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coupon_codes")
public class CouponCode {

    @Id
    private String couponCode;

    @Min(0)
    private int times_used;

    @Min(0)
    private int use_limit = 5;

    @NotNull
    private boolean status;

    @NotNull
    @FutureOrPresent
    @Column(
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;

    @FutureOrPresent
    private LocalDateTime lastModifiedAt;

    @NotNull
    @OneToOne
    @JoinColumn(
            name = "created_by"
    )
    private User createdBy;

    @OneToOne
    @JoinColumn(
            name = "last_modified_by"
    )
    private User lastModifiedBy;

    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;


}
