package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.MissionDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Mission;
import org.youcode.easybank.exceptions.MissionException;

import java.sql.*;
import java.util.Optional;

public class MissionDaoImpl implements MissionDao {

    private final Connection conn;

    public MissionDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public MissionDaoImpl(Connection connection) {
        conn = connection;
    }
    @Override
    public Optional<Mission> create(Mission mission) throws MissionException {
        String insertSQL = "INSERT INTO missions (nom, description) VALUES (?, ?) RETURNING code";

        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, mission.get_name());
            preparedStatement.setString(2, mission.get_description());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new MissionException("Creating mission failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int missionCode = generatedKeys.getInt(1);
                    mission.set_code(missionCode);
                    return Optional.of(mission);
                } else {
                    throw new MissionException("Creating mission failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new MissionException("Error creating mission: " + e.getMessage());
        }
    }

    @Override
    public Optional<Mission> getByNumber(int code) throws MissionException {
        String selectSQL = "SELECT * FROM missions WHERE code = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int missionCode = resultSet.getInt("code");
                String name = resultSet.getString("nom");
                String description = resultSet.getString("description");

                Mission mission = new Mission(name, description);
                mission.set_code(missionCode);

                return Optional.of(mission);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MissionException("Error retrieving mission by code: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(int code) throws MissionException {
        String deleteSQL = "DELETE FROM missions WHERE code = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, code);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new MissionException("Error deleting mission by code: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM missions");
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
