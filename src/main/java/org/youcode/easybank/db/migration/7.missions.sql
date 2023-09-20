CREATE TABLE missions (
    code SERIAL PRIMARY KEY,
    nom VARCHAR(255),
    description VARCHAR(255),
    employeeMatricule INT,
    FOREIGN KEY (employeeMatricule) REFERENCES employees(matricule)
);
