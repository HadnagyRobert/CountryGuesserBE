package com.example.countryguesser.configuration.db;

import com.example.countryguesser.domain.Continent;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {

    private CountryRepository countryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {
        if (countryRepository.count() == 0) {
            countryRepository.save(CountryEntity.builder().name("Netherlands").population(17235434).size(41850).continent(Continent.EUROPE).build());
            countryRepository.save(CountryEntity.builder().name("Bulgaria").population(6809852).size(110994).continent(Continent.EUROPE).build());
            countryRepository.save(CountryEntity.builder().name("Romania").population(18897623).size(238397).continent(Continent.EUROPE).build());
            countryRepository.save(CountryEntity.builder().name("Brazil").population(216568882).size(8515767).continent(Continent.SOUTH_AMERICA).build());
            countryRepository.save(CountryEntity.builder().name("China").population(1454083560).size(9562910).continent(Continent.ASIA).build());
        }
    }
}
