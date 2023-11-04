package com.example.countryguesser.controller;

import com.example.countryguesser.business.*;
import com.example.countryguesser.configuration.security.is_authenticated.IsAuthenticated;
import com.example.countryguesser.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/game")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final GuessCountryUseCase guessCountryUseCase;
    private final GiveUpGameUseCase giveUpGameUseCase;
    private final GetGameGuessesUseCase getGameGuessesUseCase;
    private final GetGameStatsUseCase getGameStatsUseCase;

    @IsAuthenticated
    @GetMapping
    public ResponseEntity<CreateGameResponse> createGame(@RequestHeader("Authorization") String accessToken) {
        CreateGameResponse createGameResponse = createGameUseCase.createGame(accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(createGameResponse);
    }

    @IsAuthenticated
    @PostMapping
    public ResponseEntity<GuessResponse> guess(@RequestBody @Valid GuessRequest guessRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(guessCountryUseCase.guess(guessRequest));
    }

    @IsAuthenticated
    @DeleteMapping("{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable(value = "gameId") final int gameId) {
        giveUpGameUseCase.giveUpGame(gameId);

        return ResponseEntity.noContent().build();
    }

    @IsAuthenticated
    @GetMapping("{gameId}")
    public ResponseEntity<GuessesResponse> getGame(@PathVariable(value = "gameId") final int gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(getGameGuessesUseCase.getGameGuesses(gameId));
    }

    @IsAuthenticated
    @RolesAllowed({"ADMIN"})
    @GetMapping("/adminStats")
    public ResponseEntity<GetAdminStats> getGames() {
        return ResponseEntity.status(HttpStatus.OK).body(getGameStatsUseCase.getAdminStats());
    }
}
