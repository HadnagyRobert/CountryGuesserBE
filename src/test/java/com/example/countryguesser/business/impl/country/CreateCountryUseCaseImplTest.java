package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.domain.Continent;
import com.example.countryguesser.domain.CreateCountryRequest;
import com.example.countryguesser.domain.CreateCountryResponse;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CreateCountryUseCaseImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CreateCountryUseCaseImpl createCountryUseCase;
    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Test
    void createCountryTest() {
        CreateCountryRequest request = new CreateCountryRequest("Country", 1000000, 1000, Continent.SOUTH_AMERICA);
        CountryEntity countryEntity = CountryEntity.builder()
                .name(request.getCountryName())
                .population(request.getPopulation())
                .size(request.getSize())
                .continent(request.getContinent())
                .build();

        CountryEntity saved = CountryEntity.builder()
                .id(1)
                .name(request.getCountryName())
                .population(request.getPopulation())
                .size(request.getSize())
                .continent(request.getContinent())
                .build();

        doNothing().when(messagingTemplate).convertAndSend("/topic/publicmessages", "Country created - Country, Size - 1000, Population - 1000000, Continent - SOUTH_AMERICA");

        when(countryRepository.save(countryEntity)).thenReturn(saved);

        CreateCountryResponse response = createCountryUseCase.createCountry(request);

        assertEquals(saved.getId(), response.getCountryId());
        verify(countryRepository, times(1)).save(countryEntity);
    }
}