package com.example.countryguesser.business;

import com.example.countryguesser.domain.GuessRequest;
import com.example.countryguesser.domain.GuessResponse;

public interface GuessCountryUseCase {
    GuessResponse guess(GuessRequest request);
}
