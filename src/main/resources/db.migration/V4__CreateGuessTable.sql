CREATE TABLE guess (
    id int NOT NULL AUTO_INCREMENT,
    game_id int NOT NULL,
    country_id int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES game (id),
    FOREIGN KEY (country_id) REFERENCES country (id)
);