
CREATE TABLE IF NOT EXISTS technologyEntity (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE,
    description VARCHAR NOT NULL,
    keywords VARCHAR NOT NULL
);
