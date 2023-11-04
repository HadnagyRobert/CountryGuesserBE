CREATE TABLE game (
    id           int AUTO_INCREMENT,
    country_id   int NOT NULL,
    score        int,
    user_id      int NOT NULL,
    is_finished  boolean,
    is_won       boolean,
    PRIMARY KEY (id),
    FOREIGN KEY (country_id) REFERENCES country (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);