package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.domain.HighLowEnum;
import com.example.countryguesser.persitence.entity.CountryEntity;
import org.springframework.stereotype.Component;

@Component
public class CompareCountries {
    public boolean isContinentCorrect(CountryEntity correctCountry, CountryEntity guessCountry) {
        return correctCountry.getContinent() == guessCountry.getContinent();
    }

    public HighLowEnum isPopulationCorrect(CountryEntity correctCountry, CountryEntity guessCountry) {
        if (correctCountry.getPopulation() == guessCountry.getPopulation())
            return HighLowEnum.EQUAL;
        else if (correctCountry.getPopulation() < guessCountry.getPopulation())
            return HighLowEnum.LOWER;
        else
            return HighLowEnum.HIGHER;
    }

    public HighLowEnum isSizeCorrect(CountryEntity correctCountry, CountryEntity guessCountry) {
        if (correctCountry.getSize() == guessCountry.getSize())
            return HighLowEnum.EQUAL;
        else if (correctCountry.getSize() < guessCountry.getSize())
            return HighLowEnum.LOWER;
        else
            return HighLowEnum.HIGHER;
    }
}
