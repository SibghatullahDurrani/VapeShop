package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products_contents")
public class ProductsContent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_generator")
    @SequenceGenerator(name = "content_generator", sequenceName = "content_seq", allocationSize = 1)
    @Column(
            columnDefinition = "BIGINT"
    )
    private Long id;

    @NotBlank
    @Column(
            columnDefinition = "TEXT",
            nullable = false
    )
    private String heading;

    @NotBlank
    @Column(
            columnDefinition = "TEXT",
            nullable = false
    )
    private String content;

    @ManyToOne
    @JoinColumn(
            name = "product_id"
    )
    private Product product;

}
