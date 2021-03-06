package clientside.helpers;

import java.util.ArrayList;

public class User{
    // User Attributes are username, password and an arrayList of accounts

    private String username = "";
    private String password = "";
    private ArrayList<Account> accountList = new ArrayList<>();

    // Return account list
    public ArrayList<Account> getAccountList() {
        return accountList;
    }

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
            String[] tempStringArray = tempAccountData.split(",",7);
            Account tempAccount = new Account(tempStringArray[0],
                                            tempStringArray[1],
                                            Double.parseDouble(tempStringArray[2]),
                                            Double.parseDouble(tempStringArray[3]),
                                            Integer.parseInt(tempStringArray[4]),
                                            Double.parseDouble(tempStringArray[5]));
                                            accountList.add(tempAccount);
            if(tempStringArray.length > 6){
                tempAccountData = tempStringArray[6];
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
