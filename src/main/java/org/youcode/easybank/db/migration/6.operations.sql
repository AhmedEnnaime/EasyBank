CREATE TABLE operations (
    operationNumber INT PRIMARY KEY,
    creationDate DATE,
    amount DOUBLE PRECISION,
    type VARCHAR(255),
    accountNumber VARCHAR(255),
    employeeMatricule INT,
    FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber),
    FOREIGN KEY (employeeMatricule) REFERENCES employees(matricule)
);
