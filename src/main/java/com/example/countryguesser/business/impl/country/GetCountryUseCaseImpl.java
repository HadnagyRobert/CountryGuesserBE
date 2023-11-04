package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.business.GetCountryUseCase;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.persitence.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCountryUseCaseImpl implements GetCountryUseCase {

    private final CountryRepository countryRepository;

    @Override
    public Optional<Country> getCountry(int id) {
        return countryRepository.findById(id).map(CountryConverter::convert);
    }
}
