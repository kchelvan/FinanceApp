package main.java.helpers;

import java.util.ArrayList;

public class User{
    // User Attributes are username, password and an arraylist of accounts
    // What else is there to add ?

    private String username = "";
    private String password = "";
    public ArrayList<Account> accountList = new ArrayList<>(); //TODO should probably change to private

    // Creates user
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    // Construct user from data obtained from  database
    public User(String data){
        String[] splitData = data.split(",",4);
        this.username = splitData[0];
        this.password = splitData[1];
        int numAccounts = Integer.parseInt(splitData[2]);
        
        String tempAccountData = splitData[3];
        
        for(int i = 0; i < numAccounts;i++){
            String[] tempStringArray = tempAccountData.split(",",6);
            Account tempAccount = new Account(tempStringArray[0],
                                            tempStringArray[1],
                                            Double.parseDouble(tempStringArray[2]),
                                            Double.parseDouble(tempStringArray[3]),
                                            Integer.parseInt(tempStringArray[4]));
                                            accountList.add(tempAccount);
            if(tempStringArray.length > 5){
                tempAccountData = tempStringArray[5];
            }
        }
    }

    // Add accounts to user
    public void addAccount(Account account){
        accountList.add(account);
    }

    // Converts User class to CSV
    public String toString(){
        String retString = "";
        retString += this.username + ",";
        retString += this.password + ",";
        retString += Integer.toString(accountList.size()) + ",";
        
        for(int i = 0; i < accountList.size(); i++){
            retString += accountList.get(i).toString();
            if(i != accountList.size()-1){
                retString += ",";
            }
        }
        return retString;
    }

}