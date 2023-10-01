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
}
