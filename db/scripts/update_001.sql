/* Author: fourbarman */

/*
Создание таблиц.
*/

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS seats
(
    id      SERIAL PRIMARY KEY,
    pos_row INT NOT NULL,
    cell    INT NOT NULL,
    CONSTRAINT seats_unique UNIQUE (pos_row, cell)
);

CREATE TABLE IF NOT EXISTS sessions
(
    id           SERIAL PRIMARY KEY,
    session_name text
);

CREATE TABLE IF NOT EXISTS tickets
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions (id),
    seats_id   INT NOT NULL REFERENCES seats (id),
    user_id    INT NOT NULL REFERENCES users (id),
    CONSTRAINT unique_ticket UNIQUE (session_id, seats_id)
);


