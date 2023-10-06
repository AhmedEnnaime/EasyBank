package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.PaymentDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.Payment;
import org.youcode.easybank.entities.SimpleOperation;
import org.youcode.easybank.enums.OPERATION;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PaymentDaoImpl implements PaymentDao {

    private final Connection conn;

    public PaymentDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public PaymentDaoImpl(Connection connection) {
        conn = connection;
    }
    @Override
    public Optional<Payment> create(Payment payment) {
        try {
            String insertSQL = "INSERT INTO payments (from_account, to_account, operationNumber) VALUES (?, ?, ?) RETURNING id";
            PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);

            preparedStatement.setInt(1, payment.getFrom_account().get_accountNumber());
            preparedStatement.setInt(2, payment.getTo_account().get_accountNumber());
            preparedStatement.setInt(3, payment.get_operationNumber());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt("id");

                payment.setId(generatedId);

                return Optional.of(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean updateDestinationBalance(Payment payment) {
        String updateBalanceSQL = "UPDATE accounts SET balance = ? WHERE accountNumber = ?";

        double newBalance;
        newBalance = payment.getTo_account().get_balance() + payment.get_amount();
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateBalanceSQL)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, payment.getTo_account().get_accountNumber());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateFromAccountBalance(Payment payment) {
        String updateBalanceSQL = "UPDATE accounts SET balance = ? WHERE accountNumber = ?";

        double newBalance;
        newBalance = payment.getFrom_account().get_balance() - payment.get_amount();
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateBalanceSQL)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, payment.getFrom_account().get_accountNumber());

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
            PreparedStatement ps = conn.prepareStatement("DELETE FROM payments");
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
