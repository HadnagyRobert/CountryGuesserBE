package com.example.countryguesser.controller;

import com.example.countryguesser.business.*;
import com.example.countryguesser.configuration.security.is_authenticated.IsAuthenticated;
import com.example.countryguesser.domain.Country;
import com.example.countryguesser.domain.CreateCountryRequest;
import com.example.countryguesser.domain.CreateCountryResponse;
import com.example.countryguesser.domain.GetCountriesResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@RequestMapping("/country")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class CountryController {
    private final GetCountriesUseCase getCountriesUseCase;
    private final GetCountryUseCase getCountryUseCase;
    private final CreateCoutryUseCase createCoutryUseCase;
    private final EditCountryUseCase editCountryUseCase;
    private final DeleteCountryUseCase deleteCountryUseCase;

    @IsAuthenticated
    @RolesAllowed({"ADMIN"})
    @GetMapping
    public ResponseEntity<GetCountriesResponse> getCountries() {
        return ResponseEntity.ok(getCountriesUseCase.getCountries());
    }

    @IsAuthenticated
    @RolesAllowed({"ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<Country> getCountry(@PathVariable(value = "id") final int id) {
        final Optional<Country> countryOptional = getCountryUseCase.getCountry(id);
        if(countryOptional.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(countryOptional.get());
    }

    @IsAuthenticated
    @RolesAllowed({"ADMIN"})
    @PostMapping
    public ResponseEntity<CreateCountryResponse> createCountry(@RequestBody final CreateCountryRequest country) {
        return ResponseEntity.ok().body(createCoutryUseCase.createCountry(country));
    }

    @IsAuthenticated
    @RolesAllowed({"ADMIN"})
    @PutMapping("{countryName}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "countryName") final String countryName, @RequestBody final CreateCountryRequest country) {
        return ResponseEntity.ok().body(editCountryUseCase.editCountry(countryName, country));
    }

    @IsAuthenticated
    @RolesAllowed({"ADMIN"})
    @DeleteMapping("{countryName}")
    public void deleteCountry(@PathVariable(value = "countryName") final String countryName) {
        deleteCountryUseCase.deleteCountry(countryName);
    }
}
