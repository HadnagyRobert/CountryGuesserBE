package com.example.countryguesser.business.impl.game;

import com.example.countryguesser.business.CreateGameUseCase;
import com.example.countryguesser.business.ExtractUserFromAccessToken;
import com.example.countryguesser.business.impl.country.CountryConverter;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.domain.CreateGameResponse;
import com.example.countryguesser.persitence.CountryRepository;
import com.example.countryguesser.persitence.GameRepository;
import com.example.countryguesser.persitence.UserRepository;
import com.example.countryguesser.persitence.entity.GameEntity;
import com.example.countryguesser.persitence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CreateGameUseCaseImpl implements CreateGameUseCase {
    private final GameRepository gameRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final ExtractUserFromAccessToken extractUserFromAccessToken;
    private final SecureRandom random = new SecureRandom();

    public Country getRandomCountry() {
        List<Country> countries = countryRepository.findAll()
                .stream()
                .map(CountryConverter::convert)
                .toList();

        return countries.get(random.nextInt(countries.size()));
    }

    @Override
    public CreateGameResponse createGame(String request) {
        int userId = extractUserFromAccessToken.extractUserFromAccessToken(request);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GameEntity activeGame = gameRepository.getActiveGame(userId);
        if (activeGame != null) {
            return buildResponse(activeGame);
        }

        GameEntity newGame = buildNewGame(user);
        gameRepository.save(newGame);

        return buildResponse(newGame);
    }

    private GameEntity buildNewGame(UserEntity user) {
        return GameEntity.builder()
                .country(CountryConverter.convert(getRandomCountry()))
                .score(0)
                .user(user)
                .isFinished(false)
                .isWon(false)
                .build();
    }

    private CreateGameResponse buildResponse(GameEntity gameEntity) {
        return CreateGameResponse.builder()
                .gameId(gameEntity.getId())
                .build();
    }
}
