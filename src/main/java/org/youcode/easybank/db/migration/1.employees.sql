CREATE TABLE employees (
    matricule SERIAL PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    recruitmentDate DATE,
    birthDate DATE,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(255),
    address VARCHAR(255)
);
