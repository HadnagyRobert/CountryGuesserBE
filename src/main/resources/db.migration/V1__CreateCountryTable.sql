CREATE TABLE country (
    id   int     NOT NULL AUTO_INCREMENT,
    name varchar(50),
    population int,
    size int,
    continent varchar(50),
    PRIMARY KEY (id),
    UNIQUE (name)
);