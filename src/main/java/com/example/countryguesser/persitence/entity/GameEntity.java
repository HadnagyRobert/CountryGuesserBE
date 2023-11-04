package com.example.countryguesser.persitence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "game")
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @Column(name = "score")
    private int score;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private List<GuessEntity> guesses;

    @Column(name = "is_finished")
    private boolean isFinished;

    @Column(name = "is_won")
    private boolean isWon;
}
