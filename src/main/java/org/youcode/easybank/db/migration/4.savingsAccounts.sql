CREATE TABLE savingsAccounts (
    accountNumber VARCHAR(255) PRIMARY KEY,
    interestRate DOUBLE PRECISION,
    FOREIGN KEY (accountNumber) REFERENCES accounts(accountNumber)
);
