CREATE SCHEMA IF NOT EXISTS todoismo;

CREATE TABLE todoismo.task
(
    id     SERIAL  NOT NULL
        CONSTRAINT pk_task
            PRIMARY KEY,
    description VARCHAR NOT NULL
);
