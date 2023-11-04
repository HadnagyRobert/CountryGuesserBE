package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.business.GetCountriesUseCase;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.domain.GetCountriesResponse;
import com.example.countryguesser.persitence.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetCountriesUseCaseImpl implements GetCountriesUseCase {
    private final CountryRepository countryRepository;

    @Override
    public GetCountriesResponse getCountries() {
        List<Country> countries = countryRepository.findAllByAlfabeticalOrder()
                .stream()
                .map(CountryConverter::convert)
                .toList();

        return GetCountriesResponse.builder()
                .countries(countries)
                .build();
    }
}
