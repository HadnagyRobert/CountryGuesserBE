package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.business.ExtractUserFromAccessToken;
import com.example.countryguesser.domain.CreateGameResponse;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.GameRepository;
import com.example.countryguesser.persitence.UserRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import com.example.countryguesser.persitence.entity.GameEntity;
import com.example.countryguesser.persitence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGameUseCaseImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExtractUserFromAccessToken extractUserFromAccessToken;

    @InjectMocks
    private CreateGameUseCaseImpl createGameUseCase;

    private final String request = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4NjI1MDQ5MywiZXhwIjoxNjg2MjUyMjkzLCJyb2xlcyI6WyJBRE1JTiJdLCJ1c2VySWQiOjJ9.zmKc6j7IfDgQz5-9-yAPsDTeNINOi_FJdAeP58FidJw";
    private final int userId = 2;

    @Test
    void shouldCreateNewGame() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userEntity));

        CountryEntity country = new CountryEntity();
        country.setId(1);
        country.setName("France");
        when(countryRepository.findAll()).thenReturn(List.of(country));

        when(extractUserFromAccessToken.extractUserFromAccessToken(request)).thenReturn(userId);

        when(gameRepository.getActiveGame(userId)).thenReturn(null);

        GameEntity gameEntity = GameEntity.builder()
                .country(country)
                .score(0)
                .user(userEntity)
                .isFinished(false)
                .isWon(false)
                .build();
        gameEntity.setId(100);

        when(gameRepository.save(any(GameEntity.class))).thenAnswer(invocation -> {
            GameEntity game = (GameEntity) invocation.getArguments()[0];
            game.setId(gameEntity.getId());
            return game;
        });

        CreateGameResponse response = createGameUseCase.createGame(request);

        assertEquals(100, response.getGameId());
        verify(gameRepository).save(any(GameEntity.class));
    }

    @Test
    void shouldReturnExistingGame() {
        when(extractUserFromAccessToken.extractUserFromAccessToken(request)).thenReturn(userId);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        GameEntity existingGame = new GameEntity();
        existingGame.setId(100);
        when(gameRepository.getActiveGame(userId)).thenReturn(existingGame);

        CreateGameResponse response = createGameUseCase.createGame(request);

        assertEquals(100, response.getGameId());
        verify(gameRepository, never()).save(any(GameEntity.class));
    }

}
