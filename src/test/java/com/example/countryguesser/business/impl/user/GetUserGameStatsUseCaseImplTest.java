package com.example.countryguesser.business.impl.user;

import com.example.countryguesser.business.ExtractUserFromAccessToken;
import com.example.countryguesser.domain.GetUserStats;
import com.example.countryguesser.domain.UserStatistics;
import com.example.countryguesser.persitence.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserGameStatsUseCaseImplTest {

    @InjectMocks
    private GetUserGameStatsUseCaseImpl getUserGameStatsUseCase;

    @Mock
    private ExtractUserFromAccessToken accessTokenEncoderDecoder;

    @Mock
    private GameRepository gameRepository;

    @Test
    void givenValidRepositoryData_whenGetUserGameStats_thenSuccess() {
        String mockAccessToken = "testToken";
        int mockUserId = 1;
        Object[] mockData = new Object[] {"test", BigDecimal.valueOf(0.7), BigDecimal.valueOf(3)};
        when(accessTokenEncoderDecoder.extractUserFromAccessToken(mockAccessToken)).thenReturn(mockUserId);
        when(gameRepository.getUserStatistics(mockUserId)).thenReturn(Collections.singletonList(mockData));

        GetUserStats stats = getUserGameStatsUseCase.getUserGameStats(mockAccessToken);
        assertNotNull(stats);
        assertNotNull(stats.getUserStatistics());
        assertEquals(1, stats.getUserStatistics().size());

        UserStatistics stat = stats.getUserStatistics().get(0);
        assertEquals("test", stat.getCountry());
        assertEquals(70, stat.getWinRate());
        assertEquals(3, stat.getAvgScore());
    }

    @Test
    void givenNullRepositoryData_whenGetUserGameStats_thenThrowNullPointerException() {
        String mockAccessToken = "testToken";
        int mockUserId = 1;
        when(accessTokenEncoderDecoder.extractUserFromAccessToken(mockAccessToken)).thenReturn(mockUserId);
        when(gameRepository.getUserStatistics(mockUserId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> getUserGameStatsUseCase.getUserGameStats(mockAccessToken));
    }
}
