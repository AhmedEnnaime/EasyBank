package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.CurrentAccountDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.CurrentAccount;
import org.youcode.easybank.exceptions.CurrentAccountException;
import org.youcode.easybank.exceptions.SavingsAccountException;

import java.sql.*;
import java.util.Optional;

public class CurrentAccountDaoImpl implements CurrentAccountDao {

    private final Connection conn;

    public CurrentAccountDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public CurrentAccountDaoImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<CurrentAccount> create(CurrentAccount currentAccount) throws CurrentAccountException {
        String insertSQL = "INSERT INTO currentAccounts (accountNumber, overdraft) VALUES (?, ?) RETURNING accountNumber";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, currentAccount.get_accountNumber());
            preparedStatement.setDouble(2, currentAccount.get_overdraft());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new CurrentAccountException("Creating savings account failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int accountNumber = generatedKeys.getInt(1);
                    currentAccount.set_accountNumber(accountNumber);
                } else {
                    throw new CurrentAccountException("Creating current account failed, no ID obtained.");
                }
            }

            return Optional.of(currentAccount);
        } catch (SQLException e) {
            throw new CurrentAccountException("Error creating current account: " + e.getMessage());
        }
    }

    @Override
    public Optional<CurrentAccount> update(int accountNumber, CurrentAccount currentAccount) throws CurrentAccountException {
        return Optional.empty();
    }

    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM currentAccounts");
            int rows = ps.executeUpdate();

            if (rows > 0) {
                deleted = true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }
}
