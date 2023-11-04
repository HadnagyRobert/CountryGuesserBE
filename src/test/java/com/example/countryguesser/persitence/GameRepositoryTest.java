//package com.example.countryguesser.persitence;
//
//import com.example.countryguesser.domain.Continent;
//import com.example.countryguesser.persitence.entity.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import javax.persistence.EntityManager;
//
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class GameRepositoryTest {
//
//    @Autowired
//    private GameRepository gameRepository;
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    public void getActiveGames() {
//        UserInfoEntity userInfo = UserInfoEntity.builder()
//                .firstName("First")
//                .lastName("Last")
//                .email("Email@gmail.com")
//                .build();
//
//        entityManager.persist(userInfo);
//
//        UserRoleEntity userRole = UserRoleEntity.builder()
//                .role(RoleEnum.USER)
//                .build();
//
//        entityManager.persist(userRole);
//
//        UserEntity user = UserEntity.builder()
//                .username("User")
//                .password("Password")
//                .userInfoEntity(userInfo)
//                .userRoles((Set<UserRoleEntity>) userRole)
//                .build();
//
//        entityManager.persist(user);
//
//        CountryEntity country = CountryEntity.builder()
//                .name("Country")
//                .population(100)
//                .size(200)
//                .continent(Continent.EUROPE)
//                .build();
//
//        entityManager.persist(country);
//
//        GameEntity game = GameEntity.builder()
//                .country(country)
//                .score(1)
//                .user(user)
//                .guesses(null)
//                .isFinished(false)
//                .isWon(false)
//                .build();
//
//        entityManager.persist(game);
//
//        entityManager.flush();
//        entityManager.clear();
//
//        GameEntity found = gameRepository.getActiveGame(user.getId());
//
//        assertEquals(found, game);
//    }
//}