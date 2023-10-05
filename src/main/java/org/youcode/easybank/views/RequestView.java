package org.youcode.easybank.views;

import org.youcode.easybank.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank.entities.Client;
import org.youcode.easybank.entities.Request;
import org.youcode.easybank.entities.Simulation;
import org.youcode.easybank.enums.STATE;
import org.youcode.easybank.services.RequestService;
import org.youcode.easybank.services.SimulationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RequestView {

    private RequestService requestService;

    private SimulationService simulationService;

    private ClientDaoImpl clientDao;

    public RequestView(RequestService requestService, ClientDaoImpl clientDao) {
        this.requestService = requestService;
        this.clientDao = clientDao;
    }

    public void createRequest() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter client's code");
            String codeInput = sc.nextLine();

            if (codeInput.equalsIgnoreCase("q")) {
                break;
            }

            int code = Integer.parseInt(codeInput);

            Optional<Client> retrievedClient = clientDao.findByID(code);
            if (retrievedClient.isPresent()) {
                System.out.println("Enter the amount of money you wanna borrow ");
                Double amount = sc.nextDouble();

                System.out.println("Enter the date of making that credit");
                String dateStr = sc.nextLine();

                System.out.println("Enter how many months you wanna keep paying your debts");
                Integer monthly_payment_num = sc.nextInt();

                System.out.println("Enter remarks if you have some or leave it empty");
                String remarks = sc.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try {
                    LocalDate request_date = LocalDate.parse(dateStr, formatter);
                    Simulation simulation = new Simulation(amount, monthly_payment_num, retrievedClient.get());

                    double result = simulationService.createSimulation(simulation);

                    if (result > 0) {
                        System.out.println("This is the amount of money you will pay each month :" + result + "DH do you wanna proceed with request ? press 'y' if you want or 'q' to quit");
                        String choice = sc.nextLine();
                        if (choice.equalsIgnoreCase("q")) {
                            break;
                        }else if (choice.equalsIgnoreCase("y")) {
                            Request request = new Request(request_date, amount, STATE.PENDING, remarks, monthly_payment_num, simulation);
                            Request createdRequest = requestService.createRequest(request);
                            if (createdRequest != null) {
                                System.out.println("Request created successfully");
                            }else {
                                System.out.println("Failed to create request");
                            }
                        }
                    }

                }catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format. Please try again.");
                }
            }
        }

    }

    public void getRequestByID() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter request's number");
            String numberInput = sc.nextLine();

            if (numberInput.equalsIgnoreCase("q")) {
                break;
            }

            int number = Integer.parseInt(numberInput);

            Request request = requestService.getRequestByID(number);

            if (request != null) {
                System.out.println("Request Found");
                System.out.println("Request date " + request.get_credit_date());
                System.out.println("Request amount " + request.get_amount());
                System.out.println("Request state " + request.get_state());
                System.out.println("Request remarks " + request.get_remarks());
                break;
            }else {
                System.out.println("Request with number " + number + "not found try again");
            }
        }
    }

    public void getAllRequests() {
        List<Request> requests = requestService.getAllRequests();

        if (requests.isEmpty()) {
            System.out.println("No request is made");
        }else {
            System.out.println("List of requests");
            for (Request request : requests) {
                System.out.println("Request number " + request.get_number());
                System.out.println("Request date " + request.get_credit_date());
                System.out.println("Request amount " + request.get_amount());
                System.out.println("Request remarks " + request.get_remarks());
                System.out.println("Request duration " + request.get_duration());
                System.out.println("Request state " + request.get_state());
                System.out.println("----------------------------");
            }

        }
    }
}
