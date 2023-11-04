package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.business.GuessCountryUseCase;
import com.example.countryguesser.domain.GuessRequest;
import com.example.countryguesser.domain.GuessResponse;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.GameRepository;
import com.example.countryguesser.persitence.GuessRepository;
import com.example.countryguesser.persitence.entity.CountryEntity;
import com.example.countryguesser.persitence.entity.GameEntity;
import com.example.countryguesser.persitence.entity.GuessEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuessCountryUseCaseImpl implements GuessCountryUseCase {
    private final GameRepository gameRepository;
    private final CountryRepository countryRepository;
    private final GuessRepository guessRepository;
    private final CompareCountries compareCountries;
    private boolean isCorrect;
    @Override
    public GuessResponse guess(GuessRequest request) {
        GameEntity game = gameRepository.findById(Integer.valueOf(request.getGameId()))
                .orElseThrow(() -> new RuntimeException("Game not found"));

        CountryEntity correctCountry = game.getCountry();

        Optional<CountryEntity> optionalGuessCountry = countryRepository.findByName(request.getCountryName());

        if (optionalGuessCountry.isEmpty()) {
            return GuessResponse.builder()
                    .correct(false)
                    .errorMessage("Please input a correct country")
                    .build();
        }

        CountryEntity guessCountry = optionalGuessCountry.get();

        GuessEntity guessEntity = guessRepository.findByGameAndGuess(game.getId(), guessCountry.getId());

        if (guessEntity != null) {
            return GuessResponse.builder()
                    .correct(false)
                    .errorMessage("You have already guessed this country")
                    .build();
        }

        GuessEntity guess = GuessEntity.builder()
                .game(game)
                .guess(guessCountry)
                .build();

        guessRepository.save(guess);
        game.setScore(game.getScore() + 1);

        isCorrect = false;

        if (game.getCountry().getId().equals(guessCountry.getId())) {
            game.setFinished(true);
            game.setWon(true);
            isCorrect = true;
        }

        gameRepository.save(game);
        return GuessResponse.builder()
                .correct(isCorrect)
                .countryName(guessCountry.getName())
                .continentCorrect(compareCountries.isContinentCorrect(correctCountry, guessCountry))
                .populationCorrect(compareCountries.isPopulationCorrect(correctCountry, guessCountry))
                .sizeCorrect(compareCountries.isSizeCorrect(correctCountry, guessCountry))
                .build();

    }
}
