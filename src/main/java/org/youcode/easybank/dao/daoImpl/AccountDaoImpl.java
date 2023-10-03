package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.AccountDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.*;
import org.youcode.easybank.enums.OPERATION;
import org.youcode.easybank.enums.STATUS;
import org.youcode.easybank.exceptions.AccountException;

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
    public Optional<Account> create(Account account) {
        String insertSQL = "INSERT INTO accounts (balance, clientCode, employeeMatricule, agency_code) VALUES (?, ?, ?, ?) RETURNING accountNumber";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, account.get_balance());
            preparedStatement.setInt(2, account.get_client().get_code());
            preparedStatement.setInt(3, account.get_employee().get_matricule());
            preparedStatement.setInt(4, account.get_agency().get_code());

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
        } catch (SQLException | AccountException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> update(Integer accountNumber, Account updatedAccount) {
        String updateSQL = "UPDATE accounts SET balance = ? WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setDouble(1, updatedAccount.get_balance());
            preparedStatement.setInt(2, accountNumber);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {

                return Optional.of(updatedAccount);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public boolean delete(Integer accountNumber) {
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
    public Optional<Account> findByID(Integer accountNumber) {
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
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();

        String selectSQL = "SELECT a.*, s.interestRate AS savingsInterestRate, c.overdraft AS currentOverdraft " +
                "FROM accounts a " +
                "LEFT JOIN savingsAccounts s ON a.accountNumber = s.accountNumber " +
                "LEFT JOIN currentAccounts c ON a.accountNumber = c.accountNumber ";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account;
                    if (resultSet.getDouble("savingsinterestrate") > 0) {
                        account = new SavingsAccount();
                        ((SavingsAccount) account).set_interestRate(resultSet.getDouble("savingsInterestRate"));
                    } else if (resultSet.getDouble("currentoverdraft") > 0) {
                        account = new CurrentAccount();
                        ((CurrentAccount) account).set_overdraft(resultSet.getDouble("currentOverdraft"));
                    } else {
                        account = new Account();
                    }
                    account.set_accountNumber(resultSet.getInt("accountNumber"));
                    account.set_balance(resultSet.getDouble("balance"));
                    account.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                    account.set_status(STATUS.valueOf(resultSet.getString("status")));
                    account.set_employee(new EmployeeDaoImpl().findByID(resultSet.getInt("employeematricule")).get());
                    account.set_client(new ClientDaoImpl().findByID(resultSet.getInt("clientcode")).get());
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
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
    public List<Optional<Account>> getClientAccounts(Client client) throws AccountException {
        List<Optional<Account>> clientAccounts = new ArrayList<>();

        String selectSQL = "SELECT a.*, s.interestRate AS savingsInterestRate, c.overdraft AS currentOverdraft " +
                "FROM accounts a " +
                "LEFT JOIN savingsAccounts s ON a.accountNumber = s.accountNumber " +
                "LEFT JOIN currentAccounts c ON a.accountNumber = c.accountNumber " +
                "WHERE a.clientCode = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, client.get_code());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account;
                    if (resultSet.getDouble("savingsinterestrate") > 0) {
                        account = new SavingsAccount();
                        ((SavingsAccount) account).set_interestRate(resultSet.getDouble("savingsInterestRate"));
                    } else if (resultSet.getDouble("currentoverdraft") > 0) {
                        account = new CurrentAccount();
                        ((CurrentAccount) account).set_overdraft(resultSet.getDouble("currentOverdraft"));
                    } else {
                        account = new Account();
                    }
                    account.set_accountNumber(resultSet.getInt("accountNumber"));
                    account.set_balance(resultSet.getDouble("balance"));
                    account.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                    account.set_status(STATUS.valueOf(resultSet.getString("status")));
                    clientAccounts.add(Optional.of(account));
                }
            }
        } catch (SQLException e) {
            throw new AccountException("Error retrieving client accounts: " + e.getMessage());
        }

        return clientAccounts;
    }

    @Override
    public boolean updateBalance(Account account, Operation operation) {
        String updateBalanceSQL = "UPDATE accounts SET balance = ? WHERE accountNumber = ?";

        double newBalance;
        if (operation.get_type() == OPERATION.DEPOSIT) {
            newBalance = account.get_balance() + operation.get_amount();
        } else if (operation.get_type() == OPERATION.WITHDRAWAL) {
            newBalance = account.get_balance() - operation.get_amount();
        } else {
            return false;
        }

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateBalanceSQL)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, account.get_accountNumber());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Account> getByOperationNumber(int operationNumber) throws AccountException {
        String selectSQL = "SELECT a.* " +
                "FROM accounts a " +
                "JOIN operations o ON a.accountNumber = o.accountNumber " +
                "WHERE o.operationNumber = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, operationNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = new Account();
                    account.set_accountNumber(resultSet.getInt("accountNumber"));
                    account.set_balance(resultSet.getDouble("balance"));
                    account.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                    account.set_status(STATUS.valueOf(resultSet.getString("status")));

                    return Optional.of(account);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new AccountException("Error retrieving account by operation number: " + e.getMessage());
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
