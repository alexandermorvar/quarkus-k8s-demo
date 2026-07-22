CREATE TABLE persona (
                         id BIGSERIAL PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         edad INTEGER,
                         fecha_registro TIMESTAMP NOT NULL
);