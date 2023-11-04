package com.example.countryguesser.business.impl.country;

import com.example.countryguesser.domain.Continent;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.persitence.entity.CountryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.fail;

@ExtendWith(MockitoExtension.class)
class CountryConverterTest {

    @Test
    void testConvertEntityToDomain() {
        CountryEntity entity = CountryEntity.builder()
                .id(1)
                .name("Test Country")
                .population(1000000)
                .size(1000)
                .continent(Continent.ASIA)
                .build();

        Country country = CountryConverter.convert(entity);

        assertEquals(entity.getId(), country.getId());
        assertEquals(entity.getName(), country.getName());
        assertEquals(entity.getPopulation(), country.getPopulation());
        assertEquals(entity.getSize(), country.getSize());
        assertEquals(entity.getContinent(), country.getContinent());
    }

    @Test
    void testConvertDomainToEntity() {
        Country country = Country.builder()
                .id(1)
                .name("Test Country")
                .population(1000000)
                .size(1000)
                .continent(Continent.ASIA)
                .build();

        CountryEntity entity = CountryConverter.convert(country);

        assertEquals(country.getId(), entity.getId());
        assertEquals(country.getName(), entity.getName());
        assertEquals(country.getPopulation(), entity.getPopulation());
        assertEquals(country.getSize(), entity.getSize());
        assertEquals(country.getContinent(), entity.getContinent());
    }

    @Test
    void testPrivateConstructor() {
        Constructor<CountryConverter> constructor = null;
        try {
            constructor = CountryConverter.class.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fail("Expected a constructor but none found.");
        }

        constructor.setAccessible(true);

        Constructor<CountryConverter> finalConstructor = constructor;
        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () -> {
            try {
                finalConstructor.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        assertTrue(thrown.getTargetException() instanceof AssertionError);
        assertEquals("The class should not be instantiated", thrown.getTargetException().getMessage());
    }
}