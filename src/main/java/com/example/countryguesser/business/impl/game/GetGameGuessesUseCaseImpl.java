package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.business.GetGameGuessesUseCase;
import com.example.countryguesser.domain.GuessResponse;
import com.example.countryguesser.domain.GuessesResponse;
import com.example.countryguesser.persitence.GuessRepository;
import com.example.countryguesser.persitence.entity.GuessEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetGameGuessesUseCaseImpl implements GetGameGuessesUseCase {
    private final GuessRepository guessRepository;
    private final CompareCountries compareCountries;


    @Override
    public GuessesResponse getGameGuesses(int gameId) {
        List<GuessEntity> guesses = guessRepository.getGuessesByGameId(gameId);
        List<GuessResponse> guessResponses = new java.util.ArrayList<>();

        for (GuessEntity guess: guesses) {
            guessResponses.add(GuessResponse.builder()
                    .correct(guess.getGame().getCountry().getId().equals(guess.getGuess().getId()))
                    .countryName(guess.getGuess().getName())
                    .continentCorrect(compareCountries.isContinentCorrect(guess.getGame().getCountry(), guess.getGuess()))
                    .populationCorrect(compareCountries.isPopulationCorrect(guess.getGame().getCountry(), guess.getGuess()))
                    .sizeCorrect(compareCountries.isSizeCorrect(guess.getGame().getCountry(), guess.getGuess()))
                    .build());
        }

        return GuessesResponse.builder()
                .guesses(guessResponses)
                .build();
    }
}
