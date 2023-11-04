package com.example.countryguesser.business;

import com.example.countryguesser.domain.Country;
import com.example.countryguesser.domain.CreateCountryRequest;

public interface EditCountryUseCase {
    Country editCountry(String countryName, CreateCountryRequest country);
}
