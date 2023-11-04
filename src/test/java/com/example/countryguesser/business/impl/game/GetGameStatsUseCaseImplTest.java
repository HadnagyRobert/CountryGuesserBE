package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.domain.AdminStatistics;
import com.example.countryguesser.domain.GetAdminStats;
import com.example.countryguesser.persitence.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGameStatsUseCaseImplTest {

    @InjectMocks
    private GetGameStatsUseCaseImpl getGameStatsUseCase;

    @Mock
    private GameRepository gameRepository;

    @Test
    void givenValidRepositoryData_whenGetAdminStats_thenSuccess() {
        Object[] mockData = new Object[] {"test", BigInteger.valueOf(1), BigDecimal.valueOf(0.7), BigDecimal.valueOf(3)};
        when(gameRepository.getAdminStatistics()).thenReturn(Collections.singletonList(mockData));

        GetAdminStats stats = getGameStatsUseCase.getAdminStats();
        assertNotNull(stats);
        assertNotNull(stats.getAdminStatistics());
        assertEquals(1, stats.getAdminStatistics().size());

        AdminStatistics stat = stats.getAdminStatistics().get(0);
        assertEquals("test", stat.getCountry());
        assertEquals(1, stat.getGamesPlayed());
        assertEquals(70, stat.getWinRate());
        assertEquals(3, stat.getAvgScore());

    }

    @Test
    void givenNullRepositoryData_whenGetAdminStats_thenThrowNullPointerException() {
        when(gameRepository.getAdminStatistics()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> getGameStatsUseCase.getAdminStats());
    }
}