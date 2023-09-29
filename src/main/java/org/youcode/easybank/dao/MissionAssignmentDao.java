package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Employee;
import org.youcode.easybank.entities.MissionAssignment;
import org.youcode.easybank.exceptions.MissionAssignmentException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface MissionAssignmentDao {
    public Optional<MissionAssignment> create(MissionAssignment missionAssignment) throws MissionAssignmentException;

    public boolean delete(int mission_code) throws MissionAssignmentException;

    public Optional<HashMap<Integer, Integer>> getStats() throws MissionAssignmentException;

    public List<MissionAssignment> getEmployeeAssignments(Employee employee) throws MissionAssignmentException;

    public Optional<MissionAssignment> getByID(int id) throws MissionAssignmentException;

    public boolean deleteAll();
}
