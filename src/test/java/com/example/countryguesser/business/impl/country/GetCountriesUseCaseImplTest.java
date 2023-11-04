package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.domain.Continent;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.domain.GetCountriesResponse;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCountriesUseCaseImplTest {

    @InjectMocks
    private GetCountriesUseCaseImpl getCountriesUseCase;

    @Mock
    private CountryRepository countryRepository;

    @Test
    void getCountriesTest() {
        CountryEntity countryEntity1 = new CountryEntity(1, "Country1", 1000000, 1000, Continent.ASIA);
        CountryEntity countryEntity2 = new CountryEntity(2, "Country2", 2000000, 2000, Continent.AFRICA);

        List<CountryEntity> countryEntities = List.of(countryEntity1, countryEntity2);

        Country country1 = CountryConverter.convert(countryEntity1);
        Country country2 = CountryConverter.convert(countryEntity2);

        List<Country> countries = List.of(country1, country2);

        when(countryRepository.findAllByAlfabeticalOrder()).thenReturn(countryEntities);

        GetCountriesResponse result = getCountriesUseCase.getCountries();

        assertEquals(countries, result.getCountries());

        verify(countryRepository, times(1)).findAllByAlfabeticalOrder();
    }
}