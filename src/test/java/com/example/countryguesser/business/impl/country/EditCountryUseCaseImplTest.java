package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.domain.Continent;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.domain.CreateCountryRequest;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditCountryUseCaseImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private EditCountryUseCaseImpl editCountryUseCase;

    @Test
    void editCountryTest_HappyPath() {
        String countryName = "Country";
        CreateCountryRequest request = new CreateCountryRequest("NewCountry", 1000000, 1000, Continent.SOUTH_AMERICA);
        CountryEntity existingCountryEntity = CountryEntity.builder()
                .name(countryName)
                .population(500000)
                .size(500)
                .continent(Continent.ASIA)
                .build();

        CountryEntity editedCountryEntity = CountryEntity.builder()
                .id(1)
                .name(request.getCountryName())
                .population(request.getPopulation())
                .size(request.getSize())
                .continent(request.getContinent())
                .build();

        when(countryRepository.findByName(countryName)).thenReturn(Optional.of(existingCountryEntity));
        when(countryRepository.save(any(CountryEntity.class))).thenReturn(editedCountryEntity);
        doNothing().when(messagingTemplate).convertAndSend("/topic/publicmessages", "Country edited - NewCountry, Size - 1000, Population - 1000000, Continent - SOUTH_AMERICA");

        Country result = editCountryUseCase.editCountry(countryName, request);

        assertEquals(request.getCountryName(), result.getName());
        assertEquals(request.getPopulation(), result.getPopulation());
        assertEquals(request.getSize(), result.getSize());
        assertEquals(request.getContinent(), result.getContinent());

        verify(countryRepository, times(1)).findByName(countryName);
        verify(countryRepository, times(1)).save(any(CountryEntity.class));
    }

    @Test
    void editCountryTest_CountryNotFound() {
        String countryName = "Country";
        CreateCountryRequest request = new CreateCountryRequest("NewCountry", 1000000, 1000, Continent.SOUTH_AMERICA);

        when(countryRepository.findByName(countryName)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> editCountryUseCase.editCountry(countryName, request));

        verify(countryRepository, times(1)).findByName(countryName);
        verify(countryRepository, times(0)).save(any(CountryEntity.class));
    }
}