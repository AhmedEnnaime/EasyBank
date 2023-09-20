CREATE TABLE operations (
    operationNumber SERIAL PRIMARY KEY,
    creationDate DATE,
    amount DOUBLE PRECISION,
    type VARCHAR(255),
    accountNumber INT,
    employeeMatricule INT,
    FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber),
    FOREIGN KEY (employeeMatricule) REFERENCES employees(matricule)
);
