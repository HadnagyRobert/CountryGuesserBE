package com.example.countryguesser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GuessesResponse {
    private List<GuessResponse> guesses;
}
