package org.youcode.easybank.dao;

import org.youcode.easybank.entities.MissionAssignment;
import org.youcode.easybank.exceptions.MissionAssignmentException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface MissionAssignmentDao {
    public Optional<MissionAssignment> create(MissionAssignment missionAssignment) throws MissionAssignmentException;

    public boolean delete(int mission_code);

    public HashMap<String, List> getAssignment() throws MissionAssignmentException;

    public List<MissionAssignment> getEmployeeAssignment() throws MissionAssignmentException;
}
