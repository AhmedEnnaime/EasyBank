package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.PaymentDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Payment;

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
