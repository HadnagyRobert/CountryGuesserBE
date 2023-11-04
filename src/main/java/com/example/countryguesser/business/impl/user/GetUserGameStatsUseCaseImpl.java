package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.business.ExtractUserFromAccessToken;
import com.example.countryguesser.business.GetUserGameStatsUseCase;
import com.example.countryguesser.domain.GetUserStats;
import com.example.countryguesser.domain.UserStatistics;
import com.example.countryguesser.persitence.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetUserGameStatsUseCaseImpl implements GetUserGameStatsUseCase {
    private final ExtractUserFromAccessToken accessTokenEncoderDecoder;
    private final GameRepository gameRepository;

    @Override
    public GetUserStats getUserGameStats(String accessToken) {
        int userId = accessTokenEncoderDecoder.extractUserFromAccessToken(accessToken);
        List<UserStatistics> statistics = gameRepository.getUserStatistics(userId).stream()
                .map(result -> new UserStatistics(
                        (String) result[0],
                        (((BigDecimal) result[1]).multiply(BigDecimal.valueOf(100))).floatValue(),
                        ((BigDecimal) result[2]).floatValue()))
                .toList();

        return GetUserStats.builder()
                .userStatistics(statistics)
                .build();
    }
}
