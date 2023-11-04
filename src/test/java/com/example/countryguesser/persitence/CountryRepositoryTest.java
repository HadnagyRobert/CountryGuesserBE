package com.example.countryguesser.persitence;

import com.example.countryguesser.domain.Continent;
import com.example.countryguesser.persitence.entity.CountryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void findByName() {
        CountryEntity country = CountryEntity.builder()
                .name("Country")
                .population(100)
                .size(200)
                .continent(Continent.EUROPE)
                .build();

        entityManager.persist(country);

        entityManager.flush();
        entityManager.clear();

        Optional<CountryEntity> found = countryRepository.findByName("Country");

        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), country.getName());
    }

    @Test
    void findAllByAlphabeticalOrder() {
        CountryEntity country1 = CountryEntity.builder()
                .name("ACountry")
                .population(100)
                .size(200)
                .continent(Continent.EUROPE)
                .build();

        CountryEntity country2 = CountryEntity.builder()
                .name("ZCountry")
                .population(300)
                .size(400)
                .continent(Continent.EUROPE)
                .build();

        CountryEntity country3 = CountryEntity.builder()
                .name("MCountry")
                .population(500)
                .size(600)
                .continent(Continent.EUROPE)
                .build();
        country3.setName("MCountry");

        entityManager.persist(country1);
        entityManager.persist(country2);
        entityManager.persist(country3);

        entityManager.flush();
        entityManager.clear();

        List<CountryEntity> countries = countryRepository.findAllByAlfabeticalOrder();

        assertEquals(3, countries.size());
        assertEquals(countries.get(0).getName(), country1.getName());
        assertEquals(countries.get(1).getName(), country3.getName());
        assertEquals(countries.get(2).getName(), country2.getName());
    }
}