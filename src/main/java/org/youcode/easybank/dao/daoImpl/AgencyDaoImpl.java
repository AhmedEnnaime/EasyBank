package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.AgencyDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.AgencyException;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AgencyDaoImpl implements AgencyDao {


    private final Connection conn;

    public AgencyDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public AgencyDaoImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Agency> create(Agency agency) {
        String insertSQL = "INSERT INTO agencies (name, address, phone) VALUES (?, ?, ?) RETURNING code";

        try (PreparedStatement ps = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, agency.get_name());
            ps.setString(2, agency.get_address());
            ps.setString(3, agency.get_phone());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new AgencyException("Creating agency failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int code = generatedKeys.getInt(1);
                    agency.set_code(code);
                } else {
                    throw new AgencyException("Creating agency failed, no ID obtained.");
                }
            }
            return Optional.of(agency);
        } catch (SQLException | AgencyException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Agency> update(Integer id, Agency agency) {
        return Optional.empty();
    }

    @Override
    public Optional<Agency> findByID(Integer id) {
        String selectSQL = "SELECT * FROM agencies WHERE code = ?";

        try (PreparedStatement ps = conn.prepareStatement(selectSQL)){
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Agency agency = new Agency();
                    agency.set_code(rs.getInt("code"));
                    agency.set_name(rs.getString("name"));
                    agency.set_address(rs.getString("address"));
                    agency.set_phone(rs.getString("phone"));

                    return Optional.of(agency);
                }else {
                    return Optional.empty();
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Agency> getAll() {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String deleteSQL = "DELETE FROM agencies WHERE code = ?";
        try(PreparedStatement ps = conn.prepareStatement(deleteSQL)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
