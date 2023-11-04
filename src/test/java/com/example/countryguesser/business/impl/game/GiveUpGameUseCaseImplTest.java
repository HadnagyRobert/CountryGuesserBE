package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.persitence.GameRepository;
import com.example.countryguesser.persitence.entity.GameEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiveUpGameUseCaseImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GiveUpGameUseCaseImpl giveUpGameUseCaseImpl;

    @Test
    void giveUpGame_FinishesAndLosesTheGame() {
        int gameId = 1;
        GameEntity game = new GameEntity();
        game.setFinished(false);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(gameRepository.save(any(GameEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        giveUpGameUseCaseImpl.giveUpGame(gameId);

        assertTrue(game.isFinished());
        assertFalse(game.isWon());
    }

    @Test
    void giveUpGame_ThrowsExceptionIfGameNotFound() {
        int gameId = 1;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> giveUpGameUseCaseImpl.giveUpGame(gameId));
        assertEquals("Game not found", exception.getMessage());
    }
}