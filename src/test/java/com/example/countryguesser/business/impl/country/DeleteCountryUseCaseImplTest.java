package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.persitence.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCountryUseCaseImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private DeleteCountryUseCaseImpl deleteCountryUseCase;

    @Test
    void deleteCountryTest() {
        String countryName = "Country";

        doNothing().when(countryRepository).deleteCountryEntityByName(countryName);
        doNothing().when(messagingTemplate).convertAndSend("/topic/publicmessages", "Country deleted - " + countryName);

        deleteCountryUseCase.deleteCountry(countryName);

        verify(countryRepository, times(1)).deleteCountryEntityByName(countryName);
    }
}