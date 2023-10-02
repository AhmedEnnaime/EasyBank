package org.youcode.easybank.services;

import org.youcode.easybank.dao.daoImpl.AgencyDaoImpl;
import org.youcode.easybank.entities.Agency;
import org.youcode.easybank.exceptions.AgencyException;

import java.util.Optional;

public class AgencyService {

    private AgencyDaoImpl agencyDao;

    public AgencyService(AgencyDaoImpl agencyDao) {
        this.agencyDao = agencyDao;
    }

    public void createAgency(Agency agency) {
        Optional<Agency> optionalAgency = agencyDao.create(agency);
        if (optionalAgency.isPresent()) {
            System.out.println("Agency created successfully.");
        }
    }

    public boolean getAgencyByID(Integer code) {
        Optional<Agency> retrievedAgency = agencyDao.findByID(code);
        if (retrievedAgency.isPresent()) {
            Agency existingAgency = retrievedAgency.get();
            System.out.println("Mission found:");
            System.out.println("Code: " + existingAgency.get_code());
            System.out.println("Name: " + existingAgency.get_name());
            System.out.println("Description: " + existingAgency.get_address());
            System.out.println("Phone Number: " + existingAgency.get_phone());
            return true;
        }else {
            System.out.println("Mission not found with code: " + code);
            return false;
        }
    }

    public boolean deleteAgencyByCode(Integer code) {
        return agencyDao.delete(code);
    }

    public boolean updateAgency(Integer code, Agency agency) {
        Optional<Agency> retrievedAgency = agencyDao.findByID(code);
        if (retrievedAgency.isPresent()) {
            Optional<Agency> updatedAgency = agencyDao.update(code, agency);
            return updatedAgency.isPresent();
        }else {
            return false;
        }
    }
}
