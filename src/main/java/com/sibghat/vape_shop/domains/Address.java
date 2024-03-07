package com.sibghat.vape_shop.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
    @SequenceGenerator(name = "address_generator", sequenceName = "address_seq", allocationSize = 1)
    @Column(
            columnDefinition = "BIGINT"
    )
    private Long id;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotBlank
    @Size(
            max = 5,
            min = 5
    )
    private String zip;

    @NotNull
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;


    @FutureOrPresent
    private LocalDateTime lastModifiedAt;

    @Column(
            nullable = false
    )
    private String createdBy;

    private String lastModifiedBy;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    @JsonBackReference
    private User user;

}
