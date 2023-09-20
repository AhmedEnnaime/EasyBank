CREATE TABLE missions (
    code INT PRIMARY KEY,
    nom VARCHAR(255),
    description VARCHAR(255),
    employeeMatricule INT,
    FOREIGN KEY (employeeMatricule) REFERENCES employees(matricule)
);
