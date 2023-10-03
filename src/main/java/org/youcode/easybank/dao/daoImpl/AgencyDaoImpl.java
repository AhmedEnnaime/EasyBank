package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.AgencyDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.AgencyException;
import org.youcode.easybank.exceptions.ClientException;

import java.sql.*;
import java.util.ArrayList;
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
        String updateSQL = "UPDATE agencies " +  "SET name = ?, address = ?, phone = ? " + "WHERE code = ?";

        try(PreparedStatement ps = conn.prepareStatement(updateSQL)) {
            ps.setString(1, agency.get_name());
            ps.setString(2, agency.get_address());
            ps.setString(3, agency.get_phone());
            ps.setInt(4, id);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new AgencyException("Updating agency failed, no rows affected.");
            }

            return Optional.of(agency);
        }catch (SQLException | AgencyException e) {
            e.printStackTrace();
            return Optional.empty();
        }

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
        List<Agency> allAgencies = new ArrayList<>();
        String selectSQL = "SELECT * FROM agencies";
        try (PreparedStatement ps = conn.prepareStatement(selectSQL)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Agency agency = new Agency();
                agency.set_code(rs.getInt("code"));
                agency.set_name(rs.getString("name"));
                agency.set_address(rs.getString("address"));
                agency.set_phone(rs.getString("phone"));

                allAgencies.add(agency);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return allAgencies;
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

    @Override
    public List<Agency> findByAddress(String address) {
        List<Agency> allAgencies = new ArrayList<>();
        String selectSQL = "SELECT * FROM agencies WHERE address = ?";

        try (PreparedStatement ps = conn.prepareStatement(selectSQL)){
            ps.setString(1, address);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Agency agency = new Agency();

                agency.set_code(rs.getInt("code"));
                agency.set_address(rs.getString("address"));
                agency.set_phone(rs.getString("phone"));

                allAgencies.add(agency);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return allAgencies;
    }

//    @Override
//    public Agency findAgencyByEmployee(Employee employee) {
//        String selectSQL = "SELECT * FROM agencies a JOIN employees e ON a.code = e.agency_code WHERE a.code = ?";
//
//        try (PreparedStatement ps = conn.prepareStatement(selectSQL)){
//            ps.setInt(1, employee.get_matricule());
//
//            try (ResultSet rs = ps.executeQuery()){
//                if (rs.next()) {
//                    Agency agency = new Agency();
//
//                    agency.set_code(rs.getInt("code"));
//                    agency.set_name(rs.getString("name"));
//                    agency.set_address(rs.getString("address"));
//                    agency.set_phone(rs.getString("phone"));
//
//                    return agency;
//                }
//            }
//        }catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//        return null;
//    }
}
