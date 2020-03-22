package main.java;

import main.java.helpers.Account;
import main.java.helpers.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class financeClient extends Thread{
    private Socket soc;

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    private User user;

    /**
     * Starts the client and connects to server
     */
    public void run(){
        System.out.println("Client starting...");
        do{
            System.out.println("Attempting to connect to server...");
            try {
                // connect to server
                soc = new Socket("localhost", 25565);
                System.out.println("Connection successful!");
                break;

            } catch (Exception e) {
                e.printStackTrace();

                System.err.println("Connection failed...");
                System.err.println("Trying again...");
            }
        }while(true);

        try {
            fromServer = new DataInputStream(soc.getInputStream());
            toServer = new DataOutputStream(soc.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Request a user login to the server
     *
     * @param username The inputted username of the user
     * @param password The inputted password of the user
     * @return Either a error message or a success message
     * @throws IOException
     */
    public String login(String username, String password) throws IOException {
        toServer.writeUTF("login");

        toServer.writeUTF(username);
        toServer.writeUTF(password);

        //TODO Trying flush to fix
        toServer.flush();

        String res = getResult();
        /*
        res can be:
        error(Invalid username): "Username Not Found !!!"
        error(wrong password): "Invalid Login"
        success: username + "," + password + "," + appData.get(username)
         */
        setUser(res);

        return res;
    }

    /**
     * Request to register a new user to the server
     *
     * @param username The inputted username of the new user
     * @param password The inputted password of the new user
     * @return Either a error message or a success message
     * @throws IOException
     */
    public String register(String username, String password) throws IOException {
        toServer.writeUTF("register");

        toServer.writeUTF(username);
        toServer.writeUTF(password);

        //TODO Trying flush to fix
        toServer.flush();

        String res = getResult();
        /*
        res can be:
        error(already a user with this username): "User already Exists"
        success: "User Added Sucessfully"
         */

        if(res.equals("User Added Sucessfully")){
            setUser(res);
        }
        return res;
    }

    /**
     * Adds an new blank account to the current user
     *
     * @param accountType The type of account
     * @param accountName The account name
     * @return A success message
     * @throws IOException
     */
    public String addAccount(String accountType, String accountName) throws IOException {
        Account account = new Account();
        account.addAccount(accountType, accountName);

        user.addAccount(account);

        System.out.println("Account added");
        return "Account added";
    }

    public ArrayList<Account> getAccountList() {
        return user.getAccountList();
    }


    /**
     * Deposits money into the selected account
     *
     * @param accountName The name of the target account
     * @param value The value to be added
     * @return A success message
     * @throws IOException
     */
    public String deposit(String accountName, double value) throws IOException {
        for(Account account : user.getAccountList()){
            if(account.getAccountName().equals(accountName)){
                account.deposit(value);
                System.out.println("Deposited");
                break;
            }
        }

        return "Deposited";
    }

    /**
     * Withdraws money from the selected account
     *
     * @param accountName The name of the target account
     * @param value The value to be taken out
     * @return A success message
     * @throws IOException
     */
    public String withdraw(String accountName, double value) throws IOException {
        for(Account account : user.getAccountList()){
            if(account.getAccountName().equals(accountName)){
                account.withdraw(value);
                System.out.println("Withdrew");
                break;
            }
        }

        return "Withdrew";
    }

    /**
     * Request the server to save user data into a csv
     *
     * @return A success message
     * @throws IOException
     */
    public String save()  {
        String res = "";
        try {
            toServer.writeUTF("save");
            toServer.writeUTF(user.toString());
            //TODO Trying flush to fix
            toServer.flush();
            res = getResult();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Safely end the connection to server
     *
     * @throws IOException
     */
    public void exit()  {
        try {
            toServer.writeUTF("exit");
            soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates a user based on given data
     *
     * @param data The data the user is to be created with
     */
    public void setUser(String data){
        if(!data.equals("Username Not Found !!!") || !data.equals("Invalid Login")){
            user = new User(data);
        }
    }

    /**
     * gets a result message from the server
     *
     * @return The result message from the server
     * @throws IOException
     */
    public String getResult() throws IOException {
        String res = fromServer.readUTF();
        System.out.println(res);
        return res;
    }

    public User getUser(){
        return user;
    }
}
