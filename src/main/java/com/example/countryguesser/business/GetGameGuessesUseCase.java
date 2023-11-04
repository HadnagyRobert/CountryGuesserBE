package com.example.countryguesser.business;

import com.example.countryguesser.domain.GuessesResponse;

public interface GetGameGuessesUseCase {
    GuessesResponse getGameGuesses(int gameId);
}
