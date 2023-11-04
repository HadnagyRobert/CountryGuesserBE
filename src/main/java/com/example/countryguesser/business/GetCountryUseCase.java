package com.example.countryguesser.business;

import com.example.countryguesser.domain.Country;

import java.util.Optional;

public interface GetCountryUseCase {
    Optional<Country> getCountry(int id);
}
