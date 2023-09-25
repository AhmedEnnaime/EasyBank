CREATE TABLE savingsAccounts (
    accountNumber INT PRIMARY KEY,
    interestRate DOUBLE PRECISION,
    FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
