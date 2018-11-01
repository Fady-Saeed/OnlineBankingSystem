package client;

import org.json.simple.JSONObject;

public class Protocol {
    public static enum OPERATIONS{
        REGISTER,
        LOGIN,
        CHECK_BALANCE,
        WITHDRAW,
        DEPOSIT,
        TRANSFER_TO_ACCOUNT_SAME_BANK,
        TRANSFER_TO_ACCOUNT_OTHER_BANK,
        VIEW_TRANSACTION_HISTORY,
        LOGOUT,
        EXIT
    };

    private String ip;
    private int port;

    public Protocol(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String register(String fullName, String password, double amount){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.REGISTER.toString());
        operation.put("fullName",fullName);
        operation.put("amount",amount);
        return operation.toJSONString();

    }
    public String login(String userName, String password){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.LOGIN.toString());
        operation.put("accountId",userName);
        operation.put("password",password);
        return operation.toJSONString();
    }
    public String checkBalance(){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.CHECK_BALANCE.toString());
        return operation.toJSONString();
    }
    public String withdraw(double amount){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.WITHDRAW.toString());
        operation.put("amount",amount);
        return operation.toJSONString();
    }
    public String deposit(double amount){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.DEPOSIT.toString());
        operation.put("amount",amount);
        return operation.toJSONString();
    }
    public String transfer(double amount, String accountId, String bankIp, int bankPort){
        JSONObject operation = new JSONObject();
        if(this.ip.equals(bankIp) && this.port == bankPort){
            operation.put("operation",OPERATIONS.TRANSFER_TO_ACCOUNT_SAME_BANK.toString());
        }else{
            operation.put("operation",OPERATIONS.TRANSFER_TO_ACCOUNT_OTHER_BANK.toString());
            operation.put("bankIp",bankIp);
            operation.put("bankPort",bankPort);
        }
        operation.put("accountId",accountId);
        operation.put("amount",amount);
        return operation.toJSONString();
    }
    public String transfer(double amount, String accountId){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.TRANSFER_TO_ACCOUNT_SAME_BANK.toString());
        operation.put("accountId",accountId);
        operation.put("amount",amount);
        return operation.toJSONString();

    }
    public String viewTransactionHistory(){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.VIEW_TRANSACTION_HISTORY.toString());
        return operation.toJSONString();
    }
    public String logout(){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.LOGOUT.toString());
        return operation.toJSONString();
    }
    public String exit(){
        JSONObject operation = new JSONObject();
        operation.put("operation",OPERATIONS.EXIT.toString());
        return operation.toJSONString();
    }
}
