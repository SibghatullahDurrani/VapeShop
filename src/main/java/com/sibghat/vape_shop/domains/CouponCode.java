package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    private Integer couponCode;

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

    @NotNull
    @FutureOrPresent
    private LocalDateTime lastModifiedAt;

    @NotBlank
    private String createdBy;

    @NotBlank
    private String lastModifiedBy;

    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;


}
