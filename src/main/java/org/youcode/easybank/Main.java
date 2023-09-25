package org.youcode.easybank;

import org.youcode.easybank.services.ClientService;
import org.youcode.easybank.services.EmployeeService;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD

//        EmployeeService.createEmployee();
//        ClientService.createClient();
//        try {
//            AccountService.createAccount();
//        }catch (EmployeeException | ClientException c) {
//            System.out.println(c.getMessage());
//        }

        AccountService.getAllCurrentAccounts();

=======
        ClientService.updateClient();
>>>>>>> parent of a8abe09 (EAS-10 completed create account,savingsAccount, currentAccount and their testing)
    }
}