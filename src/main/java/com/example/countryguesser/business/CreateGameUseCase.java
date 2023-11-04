package com.example.countryguesser.business;

import com.example.countryguesser.domain.CreateGameResponse;

public interface CreateGameUseCase {
    CreateGameResponse createGame(String request);
}
