package com.example.countryguesser.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGameResponse {
    private int gameId;
}
