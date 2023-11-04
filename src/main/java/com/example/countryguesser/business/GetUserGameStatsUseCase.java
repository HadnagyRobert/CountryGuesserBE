package com.example.countryguesser.business;

import com.example.countryguesser.domain.GetUserStats;

public interface GetUserGameStatsUseCase {
    GetUserStats getUserGameStats(String accessToken);
}
