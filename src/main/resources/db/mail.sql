CREATE TABLE item (
                         id SERIAL PRIMARY KEY,
                         description TEXT,
                         created TEXT,
                         done BOOLEAN,
                         user_id INTEGER,
                         FOREIGN KEY (user_id) REFERENCES users (Id)
);
CREATE TABLE users (
                      id SERIAL PRIMARY KEY,
                      name TEXT,
                      email TEXT,
                      password TEXT
);