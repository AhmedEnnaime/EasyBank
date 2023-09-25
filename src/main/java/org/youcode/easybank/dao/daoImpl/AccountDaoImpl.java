package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.AccountDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Account;
import org.youcode.easybank.enums.STATUS;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final Connection conn;

    public AccountDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public AccountDaoImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Account> create(Account account) throws AccountException {
        String insertSQL = "INSERT INTO accounts (balance, clientCode, employeeMatricule) VALUES (?, ?, ?) RETURNING accountNumber";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, account.get_balance());
            preparedStatement.setInt(2, account.get_client().get_code());
            preparedStatement.setInt(3, account.get_employee().get_matricule());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new AccountException("Creating account failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int accountNumber = generatedKeys.getInt(1);
                    account.set_accountNumber(accountNumber);
                } else {
                    throw new AccountException("Creating account failed, no ID obtained.");
                }
            }

            return Optional.of(account);
        } catch (SQLException e) {
            throw new AccountException("Error creating account: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(int accountNumber) {
        String deleteSQL = "DELETE FROM accounts WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, accountNumber);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Optional<Account> getByAccountNumber(int accountNumber) throws AccountException {
        String selectSQL = "SELECT * FROM accounts WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, accountNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = new Account();
                    account.set_accountNumber(resultSet.getInt("accountNumber"));
                    account.set_balance(resultSet.getDouble("balance"));
                    account.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                    account.set_status(STATUS.valueOf(resultSet.getString("status")));
//                    account.set_client(new ClientDaoImpl().getByCode(resultSet.getInt("clientCode")).get());
//                    account.set_employee(new EmployeeDaoImpl().getByMatricule(resultSet.getInt("employeeMatricule")).get());
                    return Optional.of(account);
                } else {
                    return Optional.empty();
                }
            }
//            } catch (ClientException | EmployeeException e) {
//                throw new RuntimeException(e);
//            }
        } catch (SQLException e) {
            throw new AccountException("Error retrieving account by account number: " + e.getMessage());
        }
    }


    @Override
    public List<Account> getByCreationDate(LocalDate date) throws AccountException {
        List<Account> accounts = new ArrayList<>();
        String selectByCreationDateSQL = "SELECT * FROM accounts WHERE creationDate = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectByCreationDateSQL)) {
            preparedStatement.setDate(1, Date.valueOf(date));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.set_accountNumber(resultSet.getInt("accountNumber"));
                    account.set_balance(resultSet.getDouble("balance"));
                    account.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                    account.set_status(STATUS.valueOf(resultSet.getString("status")));
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            throw new AccountException("Error retrieving accounts by creation date: " + e.getMessage());
        }
        return accounts;
    }


    @Override
    public List<Account> getByStatus(STATUS status) throws AccountException {
        List<Account> accounts = new ArrayList<>();
        String selectByStatusSQL = "SELECT * FROM accounts WHERE status = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectByStatusSQL)) {
            preparedStatement.setString(1, status.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.set_accountNumber(resultSet.getInt("accountNumber"));
                    account.set_balance(resultSet.getDouble("balance"));
                    account.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                    account.set_status(STATUS.valueOf(status.toString()));

                    accounts.add(account);
                }
            }

        } catch (SQLException e) {
            throw new AccountException("Error retrieving accounts by status: " + e.getMessage());
        }
        return accounts;
    }

    @Override
    public boolean updateStatus(int accountNumber, STATUS newStatus) {
        String updateStatusSQL = "UPDATE accounts SET status = ? WHERE accountNumber = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateStatusSQL)) {
            preparedStatement.setString(1, newStatus.toString());
            preparedStatement.setInt(2, accountNumber);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM accounts");
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
