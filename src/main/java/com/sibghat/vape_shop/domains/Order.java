package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_generator")
    @SequenceGenerator(name = "orders_generator", sequenceName = "orders_seq", allocationSize = 1)
    private Integer id;

    @NotNull
    private OrderStatus Status = OrderStatus.SHIPPED;

    private boolean couponUsed;

    @DecimalMax("100000.00")
    @Positive
    @Column(
            scale = 2,
            precision = 8
    )
    private BigDecimal total;

    @NotNull
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime lastModifiedAt;

    @NotBlank
    private String lastModifiedBy;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    private enum OrderStatus{
        SHIPPED,
        RETURNED,
        COMPLETED
    }
}
