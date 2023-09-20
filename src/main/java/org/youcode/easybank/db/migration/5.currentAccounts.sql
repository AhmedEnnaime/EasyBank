CREATE TABLE currentAccounts (
    accountNumber INT PRIMARY KEY,
    overdraft DOUBLE PRECISION,
    FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber)
);
