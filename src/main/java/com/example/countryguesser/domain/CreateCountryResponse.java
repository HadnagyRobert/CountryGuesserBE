package com.example.countryguesser.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateCountryResponse {
    private final int countryId;
}
