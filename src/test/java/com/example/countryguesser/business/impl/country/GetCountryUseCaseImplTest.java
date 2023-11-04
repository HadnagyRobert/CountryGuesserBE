package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.domain.Continent;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCountryUseCaseImplTest {

    @InjectMocks
    private GetCountryUseCaseImpl getCountryUseCase;

    @Mock
    private CountryRepository countryRepository;

    @Test
     void getCountryTest_HappyPath() {
        int id = 1;
        CountryEntity countryEntity = new CountryEntity(1, "Country1", 1000000, 1000, Continent.ASIA);
        Country country = CountryConverter.convert(countryEntity);

        when(countryRepository.findById(id)).thenReturn(Optional.of(countryEntity));

        Optional<Country> result = getCountryUseCase.getCountry(id);

        assertTrue(result.isPresent());
        assertEquals(country, result.get());

        verify(countryRepository, times(1)).findById(id);
    }

    @Test
     void getCountryTest_NoSuchCountry() {
        int id = 1;

        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Country> result = getCountryUseCase.getCountry(id);

        assertTrue(result.isEmpty());

        verify(countryRepository, times(1)).findById(id);
    }
}