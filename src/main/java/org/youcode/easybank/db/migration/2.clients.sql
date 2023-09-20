CREATE TABLE clients (
    code SERIAL PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    birthDate DATE,
    phone VARCHAR(255),
    address VARCHAR(255)
);
