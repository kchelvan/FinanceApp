package serverside;

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

/*
* A database class for saving and retreving user data
*/
public class Database{

    // One stores username and password, other stores username and data
    HashMap<String,String> userData = new HashMap<>();
    HashMap<String,String> appData = new HashMap<>();

    String tempFile = "data/database.csv";

    // Default constructor, Loads data from file to hashmaps
    public Database(){
        userData.clear();
        try{
            Scanner savedData = new Scanner(new File(tempFile));
            while(savedData.hasNextLine()){
                String[] tempStringArray = savedData.nextLine().split(",",3);
                userData.put(tempStringArray[0], tempStringArray[1]);
                if(tempStringArray.length > 2){
                    appData.put(tempStringArray[0], tempStringArray[2]);
                }else{
                    appData.put(tempStringArray[0],"");
                }
            }
            savedData.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // Check if user exists in database
    public boolean userExists(String username){
        return userData.containsKey(username);
    }

    // Check if password matches username
    public boolean checkPassword(String username, String password){
        return userData.get(username).equals(password);
    }

    // Login with the given username and password
    // Returns saved user data if username and password match
    public String login(String username, String password){
        if(!userExists(username)){
            return "ERROR: Username Not Found !!!";
        }
        if(!checkPassword(username, password)){
            return "ERROR: Invalid Login !!!";
        }
        return username + "," + password + "," + appData.get(username);
    }

    // Add user to database
    public String addUser(String username, String password){
        if(userExists(username)){
            return "ERROR: User already Exists";
        }
        userData.put(username,password);
        appData.put(username, "");
        return "User Added Successfully";
    }

    //Updates the database with given data then save files
    //Data is in user.toString() form
    public String saveDatabase(String data){
        String[] tempStringArray = data.split(",",3);
        if(!userExists(tempStringArray[1])){
            addUser(tempStringArray[0], tempStringArray[1]);
        }
        if(tempStringArray.length > 2){
            appData.put(tempStringArray[0],tempStringArray[2]);
        }
        saveFile();
        return "Databased saved sucessfully";
    }

    //Save Database to File
    public void saveFile(){
        try{
            PrintWriter writer = new PrintWriter(new File(tempFile));
            for(String user : userData.keySet()){
                String line = user + "," + userData.get(user) + "," + appData.get(user);
                writer.println(line);
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}