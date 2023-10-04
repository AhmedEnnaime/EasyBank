package org.youcode.easybank.services;

import org.youcode.easybank.dao.daoImpl.RequestDaoImpl;
import org.youcode.easybank.entities.Request;

import java.util.Optional;

public class RequestService {

    private RequestDaoImpl requestDao;

    public RequestService(RequestDaoImpl requestDao) {
        this.requestDao = requestDao;
    }

    public Request createRequest(Request request) {
        Optional<Request> optionalRequest = requestDao.create(request);
        return optionalRequest.orElse(null);
    }
}
