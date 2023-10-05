package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.SimpleOperationDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.SimpleOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class SimpleOperationDaoImpl implements SimpleOperationDao {

    private final Connection conn;

    public SimpleOperationDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public SimpleOperationDaoImpl(Connection connection) {
        conn = connection;
    }
    @Override
    public Optional<SimpleOperation> create(SimpleOperation simpleOperation) {
        String insertSimpleOperationSql = "INSERT INTO simpleOperations (type, accountNumber, operationNumber) VALUES (?, ?, ?)";

        try (PreparedStatement insertSimpleOperationStatement = conn.prepareStatement(insertSimpleOperationSql, Statement.RETURN_GENERATED_KEYS)) {
            insertSimpleOperationStatement.setString(1, simpleOperation.get_type().name());
            insertSimpleOperationStatement.setInt(2, simpleOperation.getAccount().get_accountNumber());
            insertSimpleOperationStatement.setInt(3, simpleOperation.get_operationNumber());

            int affectedRows = insertSimpleOperationStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating simple operation failed, no rows affected.");
            }
            return Optional.of(simpleOperation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
