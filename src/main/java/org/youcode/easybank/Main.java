package org.youcode.easybank;

import org.youcode.easybank.exceptions.AccountException;
import org.youcode.easybank.exceptions.ClientException;
import org.youcode.easybank.exceptions.EmployeeException;
import org.youcode.easybank.exceptions.OperationException;
import org.youcode.easybank.services.*;

public class Main {
    public static void main(String[] args) {

//        EmployeeService.createEmployee();
//        ClientService.createClient();

//        try {
//            AccountService.createAccount();
//        }catch (ClientException | EmployeeException a) {
//            a.printStackTrace();
//        }
//            AccountService.updateAccount();

//        try {
//            OperationService.createOperation();
//        }catch (AccountException | EmployeeException e) {
//            throw new RuntimeException(e);
//        }

        MissionService.deleteMission();

    }
}