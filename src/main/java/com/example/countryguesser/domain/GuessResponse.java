package com.example.countryguesser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GuessResponse {
    private boolean correct;
    private String countryName;
    private boolean continentCorrect;
    private HighLowEnum populationCorrect;
    private HighLowEnum sizeCorrect;
    private String errorMessage;
}
