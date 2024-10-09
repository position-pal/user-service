CREATE DATABASE users_and_groups;

\c users_and_groups;

CREATE TABLE groups (
                        id VARCHAR PRIMARY KEY,
                        name VARCHAR NOT NULL,
                        created_by VARCHAR NOT NULL
);

-- Crea la tabella "users"
CREATE TABLE users (
                       id VARCHAR PRIMARY KEY,
                       name VARCHAR NOT NULL,
                       surname VARCHAR NOT NULL,
                       email VARCHAR NOT NULL UNIQUE,
                       password VARCHAR NOT NULL,
                       role VARCHAR NOT NULL
);

CREATE TABLE membership (
                            user_id VARCHAR NOT NULL,
                            group_id VARCHAR NOT NULL,
                            PRIMARY KEY (user_id, group_id),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
);