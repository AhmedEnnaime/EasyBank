package org.youcode.easybank.dao.daoImpl;

import org.youcode.easybank.dao.MissionAssignmentDao;
import org.youcode.easybank.db.DBConnection;
import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.MissionAssignment;
import org.youcode.easybank.exceptions.MissionAssignmentException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MissionAssignmentDaoImpl implements MissionAssignmentDao {

    private final Connection conn;

    public MissionAssignmentDaoImpl() {
        conn = DBConnection.getInstance().establishConnection();
    }

    public MissionAssignmentDaoImpl(Connection connection) {
        conn = connection;
    }
    @Override
    public Optional<MissionAssignment> create(MissionAssignment missionAssignment) throws MissionAssignmentException {
        String insertSQL = "INSERT INTO missionAssignments (debut_date, end_date, mission_code, employee_matricule) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            for (Employee employee : missionAssignment.get_employees()) {
                preparedStatement.setDate(1, java.sql.Date.valueOf(missionAssignment.get_debutDate()));
                preparedStatement.setDate(2, java.sql.Date.valueOf(missionAssignment.get_endDate()));
                preparedStatement.setInt(3, missionAssignment.get_mission().get_code());
                preparedStatement.setInt(4, employee.get_matricule());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new MissionAssignmentException("Creating mission assignment failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int assignmentId = generatedKeys.getInt(1);
                        missionAssignment.setId(assignmentId);
                        return Optional.of(missionAssignment);
                    } else {
                        throw new MissionAssignmentException("Creating mission assignment failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new MissionAssignmentException("Error creating mission assignment: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) throws MissionAssignmentException {
        String deleteSQL = "DELETE FROM missionAssignments WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new MissionAssignmentException("Deleting mission assignment failed, no rows affected.");
            }

            return true;
        } catch (SQLException e) {
            throw new MissionAssignmentException("Error deleting mission assignment: " + e.getMessage());
        }
    }


    @Override
    public Optional<HashMap<Integer, Integer>> getStats() throws MissionAssignmentException {
        HashMap<Integer, Integer> stats = new HashMap<>();
        String sql = "SELECT mission_code, COUNT(employee_matricule) AS total FROM missionAssignments GROUP BY mission_code;";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int mission_code = resultSet.getInt("mission_code");
                int total = resultSet.getInt("total");

                stats.put(mission_code, total);
            }
        } catch (SQLException e) {
            throw new MissionAssignmentException("Error retrieving mission stats: " + e.getMessage());
        }

        return Optional.of(stats);
    }

    @Override
    public List<MissionAssignment> getEmployeeAssignments(Employee employee) throws MissionAssignmentException {
        List<MissionAssignment> assignments = new ArrayList<>();
        String selectSQL = "SELECT * FROM missionAssignments WHERE employee_matricule = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, employee.get_matricule());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MissionAssignment missionAssignment = new MissionAssignment();
                missionAssignment.setId(resultSet.getInt("id"));
                missionAssignment.set_debutDate(resultSet.getDate("debut_date").toLocalDate());
                missionAssignment.set_endDate(resultSet.getDate("end_date").toLocalDate());

                assignments.add(missionAssignment);
            }
        } catch (SQLException e) {
            throw new MissionAssignmentException("Error retrieving mission assignments for employee: " + e.getMessage());
        }

        return assignments;
    }


    @Override
    public Optional<MissionAssignment> getByID(int id) throws MissionAssignmentException {
        String selectSQL = "SELECT * FROM missionAssignments WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                MissionAssignment missionAssignment = new MissionAssignment();
                missionAssignment.setId(resultSet.getInt("id"));
                missionAssignment.set_debutDate(resultSet.getDate("debut_date").toLocalDate());
                missionAssignment.set_endDate(resultSet.getDate("end_date").toLocalDate());

                return Optional.of(missionAssignment);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new MissionAssignmentException("Error retrieving mission assignment by ID: " + e.getMessage());
        }
    }


    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM missionAssignments");
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
