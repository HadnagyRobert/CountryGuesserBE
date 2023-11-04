package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.domain.GuessResponse;
import com.example.countryguesser.domain.GuessesResponse;
import com.example.countryguesser.domain.HighLowEnum;
import com.example.countryguesser.persitence.GuessRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import com.example.countryguesser.persitence.entity.GameEntity;
import com.example.countryguesser.persitence.entity.GuessEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGameGuessesUseCaseImplTest {

    @Mock
    private GuessRepository guessRepository;

    @Mock
    private CompareCountries compareCountries;

    @InjectMocks
    private GetGameGuessesUseCaseImpl getGameGuessesUseCaseImpl;

    @Test
    void getGameGuesses_ReturnsCorrectGuessesResponse() {
        int gameId = 1;

        GuessEntity guessEntity1 = new GuessEntity();
        guessEntity1.setGame(new GameEntity());
        guessEntity1.getGame().setCountry(new CountryEntity());
        guessEntity1.getGame().getCountry().setId(1);
        guessEntity1.setGuess(new CountryEntity());
        guessEntity1.getGuess().setId(1);
        guessEntity1.getGuess().setName("Country1");

        GuessEntity guessEntity2 = new GuessEntity();
        guessEntity2.setGame(new GameEntity());
        guessEntity2.getGame().setCountry(new CountryEntity());
        guessEntity2.getGame().getCountry().setId(2);
        guessEntity2.setGuess(new CountryEntity());
        guessEntity2.getGuess().setId(3);
        guessEntity2.getGuess().setName("Country2");

        List<GuessEntity> guesses = Arrays.asList(guessEntity1, guessEntity2);
        when(guessRepository.getGuessesByGameId(gameId)).thenReturn(guesses);
        when(compareCountries.isContinentCorrect(any(), any())).thenReturn(true);
        when(compareCountries.isPopulationCorrect(any(), any())).thenReturn(HighLowEnum.EQUAL);
        when(compareCountries.isSizeCorrect(any(), any())).thenReturn(HighLowEnum.EQUAL);

        GuessesResponse response = getGameGuessesUseCaseImpl.getGameGuesses(gameId);

        assertNotNull(response);
        assertNotNull(response.getGuesses());
        assertEquals(2, response.getGuesses().size());

        GuessResponse responseGuess1 = response.getGuesses().get(0);
        assertTrue(responseGuess1.isCorrect());
        assertEquals("Country1", responseGuess1.getCountryName());
        assertTrue(responseGuess1.isContinentCorrect());
        assertEquals(HighLowEnum.EQUAL, responseGuess1.getPopulationCorrect());
        assertEquals(HighLowEnum.EQUAL, responseGuess1.getSizeCorrect());

        GuessResponse responseGuess2 = response.getGuesses().get(1);
        assertFalse(responseGuess2.isCorrect());
        assertEquals("Country2", responseGuess2.getCountryName());
        assertTrue(responseGuess2.isContinentCorrect());
        assertEquals(HighLowEnum.EQUAL, responseGuess1.getPopulationCorrect());
        assertEquals(HighLowEnum.EQUAL, responseGuess1.getSizeCorrect());
    }
}