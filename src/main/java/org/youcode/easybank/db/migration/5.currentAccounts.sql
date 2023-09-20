CREATE TABLE currentAccounts (
    accountNumber VARCHAR(255) PRIMARY KEY,
    overdraft DOUBLE PRECISION,
    FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber)
);
