package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.business.DeleteCountryUseCase;
import com.example.countryguesser.persitence.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteCountryUseCaseImpl implements DeleteCountryUseCase {
    private final CountryRepository countryRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    @Override
    public void deleteCountry(String countryName) {
        countryRepository.deleteCountryEntityByName(countryName);

        String message = String.format("Country deleted - %s", countryName);

        messagingTemplate.convertAndSend("/topic/publicmessages", message);
    }
}
