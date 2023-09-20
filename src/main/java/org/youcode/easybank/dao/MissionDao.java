package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Mission;
import org.youcode.easybank.exceptions.MissionException;

import java.util.List;

public interface MissionDao {
    public Mission create(Mission mission) throws MissionException;

    public List<Mission> getAll() throws MissionException;

    public boolean delete(int code) throws MissionException;
}
