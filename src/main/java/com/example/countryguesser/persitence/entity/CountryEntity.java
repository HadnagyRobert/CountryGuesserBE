package com.example.countryguesser.persitence.entity;

import com.example.countryguesser.domain.Continent;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "country")
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @NotBlank
    private String name;
    @Column(name = "population")
    @NotNull
    private int population;
    @Column(name = "size")
    @NotNull
    private int size;
    @Column(name = "continent")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Continent continent;
}
