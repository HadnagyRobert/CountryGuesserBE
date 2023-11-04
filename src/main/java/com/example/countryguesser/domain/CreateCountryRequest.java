package com.example.countryguesser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class CreateCountryRequest {
    @NotBlank
    private  String countryName;
    @NotNull
    private  int population;
    @NotNull
    private  int size;
    @NotBlank
    private  Continent continent;
}
