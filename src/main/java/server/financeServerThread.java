package main.java.server;

import main.java.helpers.TempDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class financeServerThread extends Thread {
    private financeServer fs;

    private Socket soc;

    private boolean alive = true;

    private DataInputStream fromClient;
    private DataOutputStream toClient;

    private TempDatabase db;

    /**
     * The constructor for the thread
     * Thread allows for multiple users to connect at once
     *
     * @param soc The socket that listens for a new user
     * @param fs The server object
     * @throws IOException
     */
    financeServerThread(Socket soc, financeServer fs) throws IOException {
        System.out.println("Starting user thread...");

        this.fs = fs;
        this.soc = soc;

        fromClient = new DataInputStream(soc.getInputStream());
        toClient = new DataOutputStream(soc.getOutputStream());

        db = new TempDatabase();
    }

    /**
     * Runs the thread and listens for client instructions
     */
    public void run() {
        System.out.println("User thread running...");
        System.out.println("Waiting for instructions...");

        while(alive){
            try {
                if(alive) {
                    String task = fromClient.readUTF();
                    decideTask(task);
                }
                else{
                    closeSocket();
                }
            } catch (IOException e) {
                try {
                    closeSocket();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Decides what instructions to execute
     *
     * @param task The name of the task it is to execute
     * @throws IOException
     */
    public void decideTask(String task) throws IOException {
        if(task.equals("login")){
            login();
        }
        else if(task.equals("register")){
            register();
        }
        else if(task.equals("save")){
            save();
        }
        else if(task.equals("exit")){
            closeSocket();
        }
    }

    /**
     * Logs the user in with data given by client
     *
     * @throws IOException
     */
    public void login() throws IOException {
        String username = fromClient.readUTF();
        String password = fromClient.readUTF();

        String res = db.login(username, password);
        toClient.writeUTF(res);
    }

    /**
     * Checks if User already registered
     * Adds a new user based on data from the client if user not registered
     *
     * @throws IOException
     */
    public void register() throws IOException {
        String username = fromClient.readUTF();
        String password = fromClient.readUTF();
        if(!db.userExists(username)) {
            String res = db.addUser(username, password);
            toClient.writeUTF(res);
        } else {
            //TODO Return an error for when false? Not sure how to fill it out
        }
    }

    /**
     * Saves user data into a csv based on data from client
     *
     * @throws IOException
     */
    public void save() throws IOException {
//        int size = fromClient.readInt();

//        for(int i = 0; i < size; i++){
//            db.saveDatabase(fromClient.readUTF());
//        }

        db.saveDatabase(fromClient.readUTF());

        toClient.writeUTF("Saved");
    }

    /**
     * Closes the socket
     */
    public void closeSocket() throws IOException {
        System.out.println("Closing socket...");
        alive = false;
        soc.close();
        fs.removeUser(this);
    }
}
