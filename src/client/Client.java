package client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException
    {
        System.out.print("IP: ");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();

        System.out.print("Port: ");
        int port = scanner.nextInt();
        scanner.nextLine();
        ClientHandler handler = new ClientHandler(ip, port);

        // System.out.println("Connecting...");
        handler.connect();

        System.out.println("Connected!");


        Protocol bankingProtocol = new Protocol(ip,port);

        while(true){
            // System.out.println("Waiting Command...");
            boolean breakLoop = handleUserRequest(handler,bankingProtocol,scanner);
            // System.out.println("Waiting Response...");
            handleServerResponse(handler);
            if(breakLoop) break;
        }
        // System.out.println("Closing...");
        handler.close();
    }

    private static boolean handleUserRequest(ClientHandler handler, Protocol bankingProtocol, Scanner scanner) throws IOException
    {
        System.out.print("Enter Command: ");
        String operation = scanner.nextLine();
        boolean breakLoop = false;
        if(operation.equalsIgnoreCase(Protocol.OPERATIONS.REGISTER.toString())) {
            System.out.print("Full Name: ");
            String fullName = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Initial Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            handler.getDOS().writeUTF(bankingProtocol.register(fullName, password, amount));
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.LOGIN.toString())) {
            System.out.print("Account ID: ");
            String accountId = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            handler.getDOS().writeUTF(bankingProtocol.login(accountId,password));
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.CHECK_BALANCE.toString())) {
            handler.getDOS().writeUTF(bankingProtocol.checkBalance());
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.WITHDRAW.toString())) {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            handler.getDOS().writeUTF(bankingProtocol.withdraw(amount));
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.DEPOSIT.toString())) {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            handler.getDOS().writeUTF(bankingProtocol.deposit(amount));
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.TRANSFER_TO_ACCOUNT_SAME_BANK.toString())) {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Recipient Account ID: ");
            String accountId = scanner.nextLine();
            handler.getDOS().writeUTF(bankingProtocol.transfer(amount,accountId));
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.TRANSFER_TO_ACCOUNT_OTHER_BANK.toString())) {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Recipient Account ID: ");
            String accountId = scanner.nextLine();
            System.out.print("Recipient Bank Server IP: ");
            String bankIp = scanner.nextLine();
            System.out.print("Recipient Bank Port: ");
            int bankPort = scanner.nextInt();
            scanner.nextLine();
            handler.getDOS().writeUTF(bankingProtocol.transfer(amount,accountId,bankIp,bankPort));
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.VIEW_TRANSACTION_HISTORY.toString())) {
            handler.getDOS().writeUTF(bankingProtocol.viewTransactionHistory());
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.LOGOUT.toString())) {
            handler.getDOS().writeUTF(bankingProtocol.logout());
        } else if(operation.equalsIgnoreCase(Protocol.OPERATIONS.EXIT.toString())) {
            handler.getDOS().writeUTF(bankingProtocol.exit());
            breakLoop = true;
        } else {
            System.out.println("Command DOES NOT Exist!");
            handleUserRequest(handler, bankingProtocol, scanner);
        }
        return breakLoop;
    }

    private static void handleServerResponse(ClientHandler handler)
    {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonMessage = (JSONObject) parser.parse(handler.getDIS().readUTF());
            System.out.println(jsonMessage.get("message"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
