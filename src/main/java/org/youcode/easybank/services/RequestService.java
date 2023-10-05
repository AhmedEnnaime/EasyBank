package org.youcode.easybank.services;

import org.youcode.easybank.dao.daoImpl.RequestDaoImpl;
import org.youcode.easybank.entities.Request;
import org.youcode.easybank.enums.STATE;

import java.util.List;
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

    public Request getRequestByID(Integer number) {
        Optional<Request> retrievedRequest = requestDao.findByID(number);
        return retrievedRequest.orElse(null);
    }

    public List<Request> getAllRequests() {
        return requestDao.getAll();
    }

    public boolean updateRequestState(Integer number, STATE state) {
        if (number.toString().isEmpty() || requestDao.findByID(number).isEmpty()) {
            return false;
        }else {
            return requestDao.updateState(number, state);
        }
    }
}
