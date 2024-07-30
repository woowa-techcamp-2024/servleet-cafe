DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS articles;

CREATE TABLE IF NOT EXISTS users (
    userId VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS articles (
    articleId VARCHAR(255) NOT NULL PRIMARY KEY,
    writer VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    contents CLOB NOT NULL,
    created VARCHAR(255) NOT NULL
);