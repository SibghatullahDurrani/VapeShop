package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pictures_generator")
    @SequenceGenerator(name = "pictures_generator", sequenceName = "pictures_seq", allocationSize = 1)
    @Column(
            columnDefinition = "BIGINT"
    )
    private Long id;

    @NotBlank
    private String path;

    @Positive
    private float size;

    @NotBlank
    private String fileType;

    @NotNull
    @FutureOrPresent
    @Column(
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;

    @FutureOrPresent
    private LocalDateTime lastModifiedAt;

    @NotBlank
    @Column(
            nullable = false
    )
    private String createdBy;

    private String lastModifiedBy;

    @ManyToOne
    @JoinColumn(
            name = "product_id"
    )
    private Product product;

}
