package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_generator")
    @SequenceGenerator(name = "categories_generator", sequenceName = "categories_seq", allocationSize = 1)
    @Column(
            columnDefinition = "BIGINT"
    )
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany
    private List<Product> products;

    @Column(
            nullable = false
    )
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime lastModifiedAt;

    @Column(
            nullable = false
    )
    private String createdBy;

    private String lastModifiedBy;

}
