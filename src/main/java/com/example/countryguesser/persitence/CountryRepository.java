package com.example.countryguesser.persitence;

import com.example.countryguesser.persitence.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
        Optional<CountryEntity> findByName(String countryName);

        @Query("SELECT c FROM CountryEntity c ORDER BY c.name ASC")
        List<CountryEntity> findAllByAlfabeticalOrder();

        void deleteCountryEntityByName(String countryName);
}
