package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.SavingsAccountDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.SavingsAccount;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.SavingsAccountException;

import java.sql.*;
import java.util.Optional;

public class SavingsAccountDaoImpl implements SavingsAccountDao {

    private final Connection conn;

    public SavingsAccountDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public SavingsAccountDaoImpl(Connection connection) {
        conn = connection;
    }
    @Override
    public Optional<SavingsAccount> create(SavingsAccount savingsAccount) throws SavingsAccountException {
        String insertSQL = "INSERT INTO savingsAccounts (accountNumber, interestRate) VALUES (?, ?) RETURNING accountNumber";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, savingsAccount.get_accountNumber());
            preparedStatement.setDouble(2, savingsAccount.get_interestRate());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SavingsAccountException("Creating savings account failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int accountNumber = generatedKeys.getInt(1);
                    savingsAccount.set_accountNumber(accountNumber);
                } else {
                    throw new SavingsAccountException("Creating savings account failed, no ID obtained.");
                }
            }

            return Optional.of(savingsAccount);
        } catch (SQLException e) {
            throw new SavingsAccountException("Error creating savings account: " + e.getMessage());
        }
    }

    @Override
    public Optional<SavingsAccount> update(int accountNumber, SavingsAccount savingsAccount) throws SavingsAccountException {
        return Optional.empty();
    }
}
