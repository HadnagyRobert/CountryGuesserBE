package com.example.countryguesser.persitence;

import com.example.countryguesser.persitence.entity.GuessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuessRepository extends JpaRepository<GuessEntity, Integer> {

    @Query("SELECT g FROM GuessEntity g WHERE g.game.id = ?1")
    List<GuessEntity> getGuessesByGameId(int gameId);

    @Query("SELECT g FROM GuessEntity g WHERE g.game.id = ?1 AND g.guess.id = ?2")
    GuessEntity findByGameAndGuess(int game, int guessCountry);
}

