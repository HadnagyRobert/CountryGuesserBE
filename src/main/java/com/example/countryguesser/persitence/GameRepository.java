package com.example.countryguesser.persitence;

import com.example.countryguesser.persitence.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<GameEntity, Integer> {

    @Query("SELECT g FROM GameEntity g WHERE g.user.id = ?1 AND g.isFinished = false")
    GameEntity getActiveGame(int userId);

    @Query(nativeQuery = true, value =
            "SELECT  c.name AS country, " +
            "COUNT(g.id) AS gamesPlayed, " +
            "AVG(CASE WHEN g.is_won = true THEN 1 ELSE 0 END) AS winRate, " +
            "COALESCE(AVG(CASE WHEN g.is_won = true THEN g.score ELSE NULL END), 0) AS avgScore " +
            "FROM country c " +
            "LEFT JOIN game g ON c.id = g.country_id  " +
            "GROUP BY c.name"
    )
    List<Object[]> getAdminStatistics();

    @Query(nativeQuery = true, value =
            "SELECT  c.name AS country, " +
            "AVG(CASE WHEN g.is_won = true THEN 1 ELSE 0 END) AS winRate, " +
            "COALESCE(AVG(CASE WHEN g.is_won = true THEN g.score ELSE NULL END), 0) AS avgScore " +
            "FROM game g " +
            "JOIN country c ON g.country_id = c.id " +
            "WHERE g.user_id = ?1 " +
            "GROUP BY c.name"
    )
    List<Object[]> getUserStatistics(int userId);
}
