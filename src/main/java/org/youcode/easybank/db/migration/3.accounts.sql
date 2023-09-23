CREATE TABLE accounts (
    accountNumber SERIAL PRIMARY KEY,
    balance DOUBLE PRECISION,
    creationDate DATE DEFAULT CURRENT_DATE,,
    status VARCHAR(255) DEFAULT 'ACTIVE',
    clientCode INT,
    employeeMatricule INT,
    FOREIGN KEY (clientCode) REFERENCES clients(code),
    FOREIGN KEY (employeeMatricule) REFERENCES employees(matricule)
);
