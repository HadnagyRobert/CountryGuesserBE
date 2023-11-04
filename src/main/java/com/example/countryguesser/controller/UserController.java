package com.example.countryguesser.controller;

import com.example.countryguesser.business.CreateUserUseCase;
import com.example.countryguesser.business.GetUserGameStatsUseCase;
import com.example.countryguesser.business.GetUserUseCase;
import com.example.countryguesser.business.LoginUseCase;
import com.example.countryguesser.configuration.security.is_authenticated.IsAuthenticated;
import com.example.countryguesser.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final LoginUseCase loginUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserGameStatsUseCase getUserGameStatsUseCase;

    @PostMapping
    public ResponseEntity<CreateUserResponse> createAccount(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginUseCase.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }

    @IsAuthenticated
    @GetMapping
    public ResponseEntity<GetUserResponse> getAccount(@RequestHeader("Authorization") String request) {
        GetUserResponse response = getUserUseCase.getUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/userStats")
    public ResponseEntity<GetUserStats> getAdminStats(@RequestHeader("Authorization") String request) {
        GetUserStats response = getUserGameStatsUseCase.getUserGameStats(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
