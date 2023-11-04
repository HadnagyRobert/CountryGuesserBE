package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.business.EditCountryUseCase;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.domain.CreateCountryRequest;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EditCountryUseCaseImpl implements EditCountryUseCase {
    private final CountryRepository countryRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Country editCountry(String countryName, CreateCountryRequest country) {
        var countryEntity = countryRepository.findByName(countryName).orElseThrow();
        countryEntity.setName(country.getCountryName());
        countryEntity.setPopulation(country.getPopulation());
        countryEntity.setSize(country.getSize());
        countryEntity.setContinent(country.getContinent());
        CountryEntity saved = countryRepository.save(countryEntity);

        String message = String.format("Country edited - %s, Size - %s, Population - %s, Continent - %s", saved.getName(), saved.getSize(), saved.getPopulation(), saved.getContinent());

        messagingTemplate.convertAndSend("/topic/publicmessages", message);

        return CountryConverter.convert(saved);

    }
}
