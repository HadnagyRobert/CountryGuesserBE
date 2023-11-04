package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.domain.Continent;
import com.example.countryguesser.domain.HighLowEnum;
import com.example.countryguesser.persitence.entity.CountryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompareCountriesTest {

    @InjectMocks
    private CompareCountries compareCountries;

    @Test
     void isContinentCorrectTest_True() {
        CountryEntity correctCountry = new CountryEntity(1, "Country1", 1000000, 1000, Continent.ASIA);
        CountryEntity guessCountry = new CountryEntity(2, "Country2", 2000000, 2000, Continent.ASIA);

        boolean result = compareCountries.isContinentCorrect(correctCountry, guessCountry);

        assertTrue(result);
    }

    @Test
    void isContinentCorrectTest_False() {
        CountryEntity correctCountry = new CountryEntity(1, "Country1", 1000000, 1000, Continent.ASIA);
        CountryEntity guessCountry = new CountryEntity(2, "Country2", 2000000, 2000, Continent.EUROPE);

        boolean result = compareCountries.isContinentCorrect(correctCountry, guessCountry);

        assertFalse(result);
    }

    @Test
    void isPopulationCorrectTest_Higher() {
        CountryEntity correctCountry = new CountryEntity(1, "Country1", 2000000, 1000, Continent.ASIA);
        CountryEntity guessCountry = new CountryEntity(2, "Country2", 1000000, 2000, Continent.ASIA);

        HighLowEnum result = compareCountries.isPopulationCorrect(correctCountry, guessCountry);

        assertEquals(HighLowEnum.HIGHER, result);
    }

    @Test
    void isPopulationCorrectTest_Lower() {
        CountryEntity correctCountry = new CountryEntity(1, "Country1", 1000000, 1000, Continent.ASIA);
        CountryEntity guessCountry = new CountryEntity(2, "Country2", 2000000, 2000, Continent.ASIA);

        HighLowEnum result = compareCountries.isPopulationCorrect(correctCountry, guessCountry);

        assertEquals(HighLowEnum.LOWER, result);
    }

    @Test
    void isPopulationCorrectTest_Equal() {
        CountryEntity correctCountry = new CountryEntity(1, "Country1", 1000000, 1000, Continent.ASIA);
        CountryEntity guessCountry = new CountryEntity(2, "Country2", 1000000, 2000, Continent.ASIA);

        HighLowEnum result = compareCountries.isPopulationCorrect(correctCountry, guessCountry);

        assertEquals(HighLowEnum.EQUAL, result);
    }

    @Test
    void isSizeCorrectTest_Higher() {
        CountryEntity correctCountry = new CountryEntity(1, "Country1", 1000000, 2000, Continent.ASIA);
        CountryEntity guessCountry = new CountryEntity(2, "Country2", 2000000, 1000, Continent.ASIA);

        HighLowEnum result = compareCountries.isSizeCorrect(correctCountry, guessCountry);

        assertEquals(HighLowEnum.HIGHER, result);
    }

    @Test
    void isSizeCorrectTest_Lower() {
        CountryEntity correctCountry = new CountryEntity(1, "Country1", 1000000, 1000, Continent.ASIA);
        CountryEntity guessCountry = new CountryEntity(2, "Country2", 2000000, 2000, Continent.ASIA);

        HighLowEnum result = compareCountries.isSizeCorrect(correctCountry, guessCountry);

        assertEquals(HighLowEnum.LOWER, result);
    }

    @Test
    void isSizeCorrectTest_Equal() {
        CountryEntity correctCountry = new CountryEntity(1, "Country1", 1000000, 1000, Continent.ASIA);
        CountryEntity guessCountry = new CountryEntity(2, "Country2", 1000000, 1000, Continent.ASIA);

        HighLowEnum result = compareCountries.isSizeCorrect(correctCountry, guessCountry);

        assertEquals(HighLowEnum.EQUAL, result);
    }
}