package com.example.countryguesser.business;

import com.example.countryguesser.domain.CreateCountryRequest;
import com.example.countryguesser.domain.CreateCountryResponse;

public interface CreateCoutryUseCase {
    CreateCountryResponse createCountry(CreateCountryRequest request);
}
