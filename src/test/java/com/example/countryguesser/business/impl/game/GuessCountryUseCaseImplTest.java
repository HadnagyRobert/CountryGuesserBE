package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.domain.GuessRequest;
import com.example.countryguesser.domain.GuessResponse;
import com.example.countryguesser.domain.HighLowEnum;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.GameRepository;
import com.example.countryguesser.persitence.GuessRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import com.example.countryguesser.persitence.entity.GameEntity;
import com.example.countryguesser.persitence.entity.GuessEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuessCountryUseCaseImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private GuessRepository guessRepository;

    @Mock
    private CompareCountries compareCountries;

    @InjectMocks
    private GuessCountryUseCaseImpl guessCountryUseCaseImpl;

    @Test
    void guess_ThrowsExceptionIfGameNotFound() {
        GuessRequest request = new GuessRequest("1", null);
        when(gameRepository.findById(Integer.valueOf(request.getGameId()))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> guessCountryUseCaseImpl.guess(request));
        assertEquals("Game not found", exception.getMessage());
    }

    @Test
    void guess_ReturnsErrorMessageIfCountryNotFound() {
        int gameId = 1;
        GameEntity game = new GameEntity();
        GuessRequest request = new GuessRequest("1", "TestCountry");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(countryRepository.findByName(request.getCountryName())).thenReturn(Optional.empty());

        GuessResponse response = guessCountryUseCaseImpl.guess(request);

        assertFalse(response.isCorrect());
        assertEquals("Please input a correct country", response.getErrorMessage());
    }

    @Test
    void guess_ReturnsErrorMessageIfCountryAlreadyGuessed() {
        int gameId = 1;
        String countryName = "TestCountry";
        GameEntity game = new GameEntity();
        game.setId(gameId);
        CountryEntity country = new CountryEntity();
        country.setId(1);
        country.setName(countryName);
        GuessRequest request = new GuessRequest("1", "TestCountry");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(countryRepository.findByName(request.getCountryName())).thenReturn(Optional.of(country));
        when(guessRepository.findByGameAndGuess(game.getId(), country.getId())).thenReturn(new GuessEntity());

        GuessResponse response = guessCountryUseCaseImpl.guess(request);

        assertFalse(response.isCorrect());
        assertEquals("You have already guessed this country", response.getErrorMessage());
    }

    @Test
    void guess_ReturnsCorrectResponseIfGuessIsCorrect() {
        int gameId = 1;
        String countryName = "TestCountry";
        GameEntity game = new GameEntity();
        game.setId(gameId);
        CountryEntity country = new CountryEntity();
        country.setId(gameId);
        country.setName(countryName);
        game.setCountry(country);
        GuessRequest request = new GuessRequest("1", "TestCountry");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(countryRepository.findByName(request.getCountryName())).thenReturn(Optional.of(country));
        when(guessRepository.findByGameAndGuess(game.getId(), country.getId())).thenReturn(null);
        when(compareCountries.isContinentCorrect(country, country)).thenReturn(true);
        when(compareCountries.isPopulationCorrect(country, country)).thenReturn(HighLowEnum.EQUAL);
        when(compareCountries.isSizeCorrect(country, country)).thenReturn(HighLowEnum.EQUAL);

        GuessResponse response = guessCountryUseCaseImpl.guess(request);

        assertTrue(response.isCorrect());
        assertEquals(countryName, response.getCountryName());
        assertTrue(response.isContinentCorrect());
        assertEquals(HighLowEnum.EQUAL, response.getPopulationCorrect());
        assertEquals(HighLowEnum.EQUAL, response.getSizeCorrect());
    }

    @Test
    void guess_ReturnsCorrectResponseIfGuessIsIncorrect() {
        int gameId = 1;
        String countryName = "TestCountry";
        GameEntity game = new GameEntity();
        game.setId(gameId);
        CountryEntity correctCountry = new CountryEntity();
        correctCountry.setId(gameId);
        game.setCountry(correctCountry);
        CountryEntity guessCountry = new CountryEntity();
        guessCountry.setId(gameId + 1);
        guessCountry.setName(countryName);
        GuessRequest request = new GuessRequest("1", "TestCountry");

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(countryRepository.findByName(request.getCountryName())).thenReturn(Optional.of(guessCountry));
        when(guessRepository.findByGameAndGuess(game.getId(), guessCountry.getId())).thenReturn(null);
        when(compareCountries.isContinentCorrect(correctCountry, guessCountry)).thenReturn(false);
        when(compareCountries.isPopulationCorrect(correctCountry, guessCountry)).thenReturn(HighLowEnum.LOWER);
        when(compareCountries.isSizeCorrect(correctCountry, guessCountry)).thenReturn(HighLowEnum.LOWER);

        GuessResponse response = guessCountryUseCaseImpl.guess(request);

        assertFalse(response.isCorrect());
        assertEquals(countryName, response.getCountryName());
        assertFalse(response.isContinentCorrect());
        assertNotEquals(HighLowEnum.EQUAL, response.getPopulationCorrect());
        assertNotEquals(HighLowEnum.EQUAL, response.getSizeCorrect());
    }
}