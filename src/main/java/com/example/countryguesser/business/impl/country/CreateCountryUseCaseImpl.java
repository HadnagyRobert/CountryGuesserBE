package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.business.CreateCoutryUseCase;
import com.example.countryguesser.domain.CreateCountryRequest;
import com.example.countryguesser.domain.CreateCountryResponse;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCountryUseCaseImpl implements CreateCoutryUseCase {
    private final CountryRepository countryRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public CreateCountryResponse createCountry(CreateCountryRequest request) {
        CountryEntity countryEntity = CountryEntity.builder()
                .name(request.getCountryName())
                .population(request.getPopulation())
                .size(request.getSize())
                .continent(request.getContinent())
                .build();

        CountryEntity saved = countryRepository.save(countryEntity);

        String message = String.format("New country created - %s, Size - %s, Population - %s, Continent - %s", saved.getName(), saved.getSize(), saved.getPopulation(), saved.getContinent());

        messagingTemplate.convertAndSend("/topic/publicmessages", message);

        return CreateCountryResponse.builder()
                .countryId(saved.getId())
                .build();
    }
}
