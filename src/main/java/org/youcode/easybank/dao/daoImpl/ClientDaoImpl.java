package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.ClientDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Client;
import org.youcode.easybank.exceptions.ClientException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    private final Connection conn;

    public ClientDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public ClientDaoImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Client> create(Client client) {
        String insertSQL = "INSERT INTO clients (firstName, lastName, birthDate, phone, address, employeeMatricule) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING code";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, client.get_firstName());
            preparedStatement.setString(2, client.get_lastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(client.get_birthDate()));
            preparedStatement.setString(4, client.get_phone());
            preparedStatement.setString(5, client.get_address());
            preparedStatement.setInt(6, client.get_employee().get_matricule());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new ClientException("Creating client failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int code = generatedKeys.getInt(1);
                    client.set_code(code);
                } else {
                    throw new ClientException("Creating client failed, no ID obtained.");
                }
            }

            return Optional.of(client);
        } catch (SQLException | ClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Optional<Client> update(Integer code, Client client) {
        String updateSQL = "UPDATE clients " +
                "SET firstName = ?, lastName = ?, birthDate = ?, phone = ?, address = ? " +
                "WHERE code = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, client.get_firstName());
            preparedStatement.setString(2, client.get_lastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(client.get_birthDate()));
            preparedStatement.setString(4, client.get_phone());
            preparedStatement.setString(5, client.get_address());
            preparedStatement.setInt(6, code);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new ClientException("Updating client failed, no rows affected.");
            }

            return Optional.of(client);
        } catch (SQLException | ClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Integer code) {
        String deleteSQL = "DELETE FROM clients WHERE code = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, code);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Client> findByID(Integer code) {
        String selectSQL = "SELECT * FROM clients WHERE code = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client();
                    client.set_code(resultSet.getInt("code"));
                    client.set_firstName(resultSet.getString("firstName"));
                    client.set_lastName(resultSet.getString("lastName"));
                    client.set_birthDate(resultSet.getDate("birthDate").toLocalDate());
                    client.set_phone(resultSet.getString("phone"));
                    client.set_address(resultSet.getString("address"));
//                    client.set_employee(new EmployeeDaoImpl().findByID(resultSet.getInt("employeematricule")).get());

                    return Optional.of(client);
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
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM clients";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {

            while (resultSet.next()) {
                Client client = new Client();
                client.set_code(resultSet.getInt("code"));
                client.set_firstName(resultSet.getString("firstName"));
                client.set_lastName(resultSet.getString("lastName"));
                client.set_birthDate(resultSet.getDate("birthDate").toLocalDate());
                client.set_phone(resultSet.getString("phone"));
                client.set_address(resultSet.getString("address"));

                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public List<Client> findByAttribute(String searchValue) throws ClientException {
        List<Client> clients = new ArrayList<>();

        String selectByAttributeSQL = "SELECT * FROM clients WHERE " +
                "firstName LIKE ? OR " +
                "lastName LIKE ? OR " +
                "birthDate::TEXT LIKE ? OR " +
                "phone LIKE ? OR " +
                "address LIKE ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectByAttributeSQL)) {
            String wildcardSearchValue = "%" + searchValue + "%";
            for (int i = 1; i <= 5; i++) {
                preparedStatement.setString(i, wildcardSearchValue);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Client client = new Client();
                    client.set_code(resultSet.getInt("code"));
                    client.set_firstName(resultSet.getString("firstName"));
                    client.set_lastName(resultSet.getString("lastName"));
                    client.set_birthDate(resultSet.getDate("birthDate").toLocalDate());
                    client.set_phone(resultSet.getString("phone"));
                    client.set_address(resultSet.getString("address"));

                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            throw new ClientException("Error searching for clients by attribute.");
        }

        return clients;
    }

    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM clients");
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
