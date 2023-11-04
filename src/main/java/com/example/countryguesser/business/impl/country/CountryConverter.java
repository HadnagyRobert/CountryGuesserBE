package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.domain.Country;
import com.example.countryguesser.persitence.entity.CountryEntity;

public class CountryConverter {
    private CountryConverter() {
        throw new AssertionError("The class should not be instantiated");
    }
    public static Country convert(CountryEntity country) {
        return Country.builder()
                .id(country.getId())
                .name(country.getName())
                .population(country.getPopulation())
                .size(country.getSize())
                .continent(country.getContinent())
                .build();
    }

    public static CountryEntity convert(Country country) {
        return CountryEntity.builder()
                .id(country.getId())
                .name(country.getName())
                .population(country.getPopulation())
                .size(country.getSize())
                .continent(country.getContinent())
                .build();
    }
}
