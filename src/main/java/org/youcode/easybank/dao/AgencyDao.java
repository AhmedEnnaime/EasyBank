package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface AgencyDao extends IData<Agency, Integer>{

    public List<Agency> findByAddress(String address);

//    public Agency findAgencyByEmployee(Employee employee);
}
