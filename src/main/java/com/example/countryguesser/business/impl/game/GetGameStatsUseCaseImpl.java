package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.business.GetGameStatsUseCase;
import com.example.countryguesser.domain.AdminStatistics;
import com.example.countryguesser.domain.GetAdminStats;
import com.example.countryguesser.persitence.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetGameStatsUseCaseImpl implements GetGameStatsUseCase {
    private final GameRepository gameRepository;

    @Override
    public GetAdminStats getAdminStats() {
        List<AdminStatistics> statistics = gameRepository.getAdminStatistics().stream()
                .map(result -> new AdminStatistics(
                        (String) result[0],
                        ((BigInteger) result[1]).intValue(),
                        (((BigDecimal) result[2]).multiply(BigDecimal.valueOf(100))).floatValue(),
                        ((BigDecimal) result[3]).floatValue()))
                .toList();

        return GetAdminStats.builder()
                .adminStatistics(statistics)
                .build();
    }
}
