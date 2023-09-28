package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.SavingsAccountDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.SavingsAccount;
import org.youcode.easybank.enums.STATUS;
import org.youcode.easybank.exceptions.SavingsAccountException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    public Optional<SavingsAccount> create(SavingsAccount savingsAccount) {
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
        } catch (SQLException | SavingsAccountException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<SavingsAccount> update(Integer accountNumber, SavingsAccount updatedSavingsAccount) {
        String updateSQL = "UPDATE savingsAccounts SET interestRate = ? WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setDouble(1, updatedSavingsAccount.get_interestRate());
            preparedStatement.setInt(2, accountNumber);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return Optional.of(updatedSavingsAccount);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
           e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public List<SavingsAccount> getAll() {
        List<SavingsAccount> accounts = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM savingsAccounts s JOIN accounts a ON s.accountNumber = a.accountNumber";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {

            while (resultSet.next()) {
                SavingsAccount account = new SavingsAccount();
                account.set_accountNumber(resultSet.getInt("accountNumber"));
                account.set_balance(resultSet.getDouble("balance"));
                account.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                account.set_status(STATUS.valueOf(resultSet.getString("status")));
                account.set_interestRate(resultSet.getDouble("interestRate"));
                account.set_client(new ClientDaoImpl().findByID(resultSet.getInt("clientCode")).get());
                account.set_employee(new EmployeeDaoImpl().findByID(resultSet.getInt("employeeMatricule")).get());
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    public Optional<SavingsAccount> findByID(Integer accountNumber) {
        String selectSQL = "SELECT * FROM savingsAccounts s JOIN accounts a ON s.accountNumber = a.accountNumber WHERE s.accountNumber = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, accountNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    SavingsAccount account = new SavingsAccount();
                    account.set_accountNumber(resultSet.getInt("accountNumber"));
                    account.set_balance(resultSet.getDouble("balance"));
                    account.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                    account.set_status(STATUS.valueOf(resultSet.getString("status")));
                    account.set_interestRate(resultSet.getDouble("interestRate"));
                    return Optional.of(account);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM savingsAccounts");
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
