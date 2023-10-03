package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.TransferDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.Transfer;
import org.youcode.easybank.exceptions.EmployeeException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransferDaoImpl implements TransferDao {

    private final Connection conn;

    public TransferDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public TransferDaoImpl(Connection connection) {
        conn = connection;
    }


    @Override
    public Optional<Transfer> transferEmployee(Transfer transfer) {
        String insertSQL = "INSERT INTO transfers(transfer_date, employee_matricule, agency_code) VALUES(?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)){
            ps.setDate(1, Date.valueOf(transfer.get_transfer_date()));
            ps.setInt(2, transfer.get_employee().get_matricule());
            ps.setInt(3, transfer.get_agency().get_code());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating transfer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    transfer.set_id(id);
                    return Optional.of(transfer);
                } else {
                    throw new Exception("Creating transfer failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Transfer> getEmployeeHistoricalTransfers(Employee employee) {
        List<Transfer> historicalTransfers = new ArrayList<>();
        String selectSQL = "SELECT * FROM transfers WHERE employee_matricule = ?";

        try (PreparedStatement ps = conn.prepareStatement(selectSQL)){
            ps.setInt(1, employee.get_matricule());

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Transfer transfer = new Transfer();
                    transfer.set_id(rs.getInt("id"));
                    transfer.set_transfer_date(rs.getDate("transfer_date").toLocalDate());
                    transfer.set_employee(new EmployeeDaoImpl().findByID(rs.getInt("employee_matricule")).get());
                    transfer.set_agency(new AgencyDaoImpl().findByID(rs.getInt("agency_code")).get());

                    historicalTransfers.add(transfer);
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return historicalTransfers;
    }


    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM transfers");
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
