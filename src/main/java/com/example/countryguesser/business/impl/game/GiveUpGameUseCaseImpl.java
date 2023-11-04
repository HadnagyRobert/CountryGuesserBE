package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.business.GiveUpGameUseCase;
import com.example.countryguesser.persitence.GameRepository;
import com.example.countryguesser.persitence.entity.GameEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiveUpGameUseCaseImpl implements GiveUpGameUseCase {
    private final GameRepository gameRepository;
    @Override
    public void giveUpGame(int gameId) {
            GameEntity game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new RuntimeException("Game not found"));
            game.setFinished(true);
            game.setWon(false);
            gameRepository.save(game);
    }
}
