package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.RequestDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Request;
import org.youcode.easybank.enums.STATE;
import org.youcode.easybank.exceptions.ClientException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestDaoImpl implements RequestDao {

    private final Connection conn;

    public RequestDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public RequestDaoImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Request> create(Request request) {
        String insertSQL = "INSERT INTO requests(credit_date, amount, remarks, duration, client_code) VALUES(?, ?, ?, ?, ?) RETURNING number";

        try (PreparedStatement ps = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)){
            ps.setDate(1, Date.valueOf(request.get_credit_date()));
            ps.setDouble(2, request.get_amount());
            ps.setString(3, request.get_remarks());
            ps.setInt(4, request.get_duration());
            ps.setInt(5, request.get_simulation().get_client().get_code());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating request failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int number = generatedKeys.getInt(1);
                    request.set_number(number);
                } else {
                    throw new ClientException("Creating request failed, no ID obtained.");
                }
            }
            return Optional.of(request);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Request> findByID(Integer number) {
        String selectSQL = "SELECT * FROM requests WHERE number = ?";

        try (PreparedStatement ps =conn.prepareStatement(selectSQL)){
            ps.setInt(1, number);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Request request = new Request();
                    request.set_number(rs.getInt("number"));
                    request.set_credit_date(rs.getDate("credit_date").toLocalDate());
                    request.set_amount(rs.getDouble("amount"));
                    request.set_remarks(rs.getString("remarks"));
                    request.set_state(STATE.valueOf(rs.getString("state")));
                    request.set_duration(rs.getInt("duration"));

                    return Optional.of(request);
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
    public List<Request> getAll() {
        List<Request> allRequests = new ArrayList<>();
        String selectSQL = "SELECT * FROM requests";

        try (PreparedStatement ps = conn.prepareStatement(selectSQL)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request request = new Request();
                request.set_number(rs.getInt("number"));
                request.set_amount(rs.getDouble("amount"));
                request.set_state(STATE.valueOf(rs.getString("state")));
                request.set_remarks(rs.getString("remarks"));
                request.set_duration(rs.getInt("duration"));

                allRequests.add(request);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return allRequests;
    }

    @Override
    public boolean updateState(Integer number, STATE state) {
        String updateSQL = "UPDATE requests SET state = ? WHERE number = ?";

        try (PreparedStatement ps = conn.prepareStatement(updateSQL)){
            ps.setString(1, String.valueOf(state));
            ps.setInt(2, number);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Request> getByState(STATE state) {
        List<Request> requests = new ArrayList<>();
        String selectSQL = "SELECT * FROM requests WHERE state = ?";

        try (PreparedStatement ps = conn.prepareStatement(selectSQL)){
            ps.setString(1, String.valueOf(state));

           try (ResultSet rs = ps.executeQuery()){
               while (rs.next()) {
                   Request request = new Request();
                   request.set_number(rs.getInt("number"));
                   request.set_amount(rs.getDouble("amount"));
                   request.set_state(STATE.valueOf(rs.getString("state")));
                   request.set_remarks(rs.getString("remarks"));
                   request.set_duration(rs.getInt("duration"));

                   requests.add(request);
               }
           }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM requests");
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
