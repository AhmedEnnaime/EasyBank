package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.OperationDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.enums.OPERATION;
import org.youcode.easybank.exceptions.OperationException;

import java.sql.*;
import java.util.Optional;

public class OperationDaoImpl implements OperationDao {

    private final Connection conn;

    public OperationDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public OperationDaoImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Operation> create(Operation operation) throws OperationException {
        String insertSql = "INSERT INTO operations (amount, type, accountNumber, employeeMatricule) " +
                "VALUES (?, ?, ?, ?) RETURNING operationNumber";

        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDouble(1, operation.get_amount());
            preparedStatement.setString(2, operation.get_type().toString());
            preparedStatement.setInt(3, operation.get_account().get_accountNumber());
            preparedStatement.setInt(4, operation.get_employee().get_matricule());

            int affectedRows = preparedStatement.executeUpdate();


            if (affectedRows == 0) {
                throw new OperationException("Creating operation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int operationNumber = generatedKeys.getInt(1);
                    operation.set_operationNumber(operationNumber);
                } else {
                    throw new OperationException("Creating operation failed, no ID obtained.");
                }
            }

            return Optional.of(operation);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationException("Error creating operation." + e.getMessage());
        }
    }
    @Override
    public boolean delete(int operationNumber) {
        String deleteSql = "DELETE FROM operations WHERE operationNumber = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, operationNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Optional<Operation> getByNumber(int operationNumber) throws OperationException {
        String selectSql = "SELECT * FROM operations WHERE operationNumber = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, operationNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Operation operation = new Operation();
                operation.set_operationNumber(resultSet.getInt("operationNumber"));
                operation.set_creationDate(resultSet.getDate("creationDate").toLocalDate());
                operation.set_amount(resultSet.getDouble("amount"));
                operation.set_type(OPERATION.valueOf(resultSet.getString("type")));

                return Optional.of(operation);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationException("Error while retrieving operation by operationNumber.");
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


