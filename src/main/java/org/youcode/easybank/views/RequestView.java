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

    public RequestView(RequestService requestService, ClientDaoImpl clientDao, SimulationService simulationService) {
        this.requestService = requestService;
        this.clientDao = clientDao;
        this.simulationService = simulationService;
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
                sc.nextLine();

                System.out.println("Enter the date of making that credit (yyyy-MM-dd):");
                String dateStr = sc.nextLine();

                System.out.println("Enter how many months you wanna keep paying your debts:");
                Integer monthly_payment_num = sc.nextInt();

                System.out.println("Enter remarks if you have some or leave it empty");
                sc.nextLine();
                String remarks = sc.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try {
                    LocalDate request_date = LocalDate.parse(dateStr, formatter);
                    Simulation simulation = new Simulation(amount, monthly_payment_num, retrievedClient.get());

                    double result = simulationService.createSimulation(simulation);

                    if (result > 0) {
                        System.out.println("This is the amount of money you will pay each month: " + String.format("%.2f", result) + " DH. Do you want to proceed with the request? Press 'y' if you want or 'q' to quit");
                        String choice = sc.nextLine();
                        if (choice.equalsIgnoreCase("q")) {
                            break;
                        } else if (choice.equalsIgnoreCase("y")) {
                            Request request = new Request(request_date, amount, STATE.PENDING, remarks, monthly_payment_num, simulation);
                            Request createdRequest = requestService.createRequest(request);
                            if (createdRequest != null) {
                                System.out.println("Request created successfully");
                                break;
                            } else {
                                System.out.println("Failed to create request");
                            }
                        }
                    }

                } catch (DateTimeParseException e) {
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
                System.out.println("Request number : " + request.get_number());
                System.out.println("Request date : " + request.get_credit_date());
                System.out.println("Request amount : " + request.get_amount());
                System.out.println("Request remarks : " + request.get_remarks());
                System.out.println("Request duration : " + request.get_duration());
                System.out.println("Request state : " + request.get_state());
                System.out.println("----------------------------");
            }

        }
    }

    public void updateRequestState() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter number of request you want to update it's state ");
            String  numberInput = sc.nextLine();

            if (numberInput.equalsIgnoreCase("q")) {
                break;
            }

            System.out.println("Enter the new state (PENDING | APPROVED | DECLINED) ");
            String state = sc.nextLine();


            int number = Integer.parseInt(numberInput);

            if(requestService.updateRequestState(number, STATE.valueOf(state))) {
                System.out.println("Request state updated successfully");
                break;
            }else {
                System.out.println("Invalid number try again or press 'q' to quit");
            }
        }
    }

    public void getRequestsByState() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the state of requests you wanna search for (PENDING | APPROVED | DECLINED) ");
        String state = sc.nextLine();

        List<Request> requests = requestService.getRequestsByState(STATE.valueOf(state));

        if (requests.isEmpty()) {
            System.out.println("No request available with that state");
        }else {
            for (Request request : requests) {
                System.out.println("Request number : " + request.get_number());
                System.out.println("Request date : " + request.get_credit_date());
                System.out.println("Request amount : " + request.get_amount());
                System.out.println("Request remarks : " + request.get_remarks());
                System.out.println("Request duration : " + request.get_duration());
                System.out.println("Request state : " + request.get_state());
                System.out.println("----------------------------");
            }
        }
    }
}
