package com.sibghat.vape_shop.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pictures")
public class Picture { // TODO add the relationship to the product domain and add validation to the attributes

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pictures_generator")
    @SequenceGenerator(name = "pictures_generator", sequenceName = "pictures_seq", allocationSize = 1)
    @Column(
            columnDefinition = "BIGINT"
    )
    private BigInteger id;

    private String path;

    private float size;

    private String fileType;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

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

}
