package main.java.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class financeServer {
    private int port = 25565;

    private ServerSocket ss = null;

    private ArrayList<financeServerThread> fst = new ArrayList<financeServerThread>();

    private int connected = 0;
    financeServer() {
        start();
    }

    public void start(){
        System.out.println("Starting server...");
        System.out.println("Creating server socket with port " + port + " ...");

        // keeps trying to crate a new Server Socket
        boolean ssCreated = false;
        do {
            try {
                ss = new ServerSocket(port);
                ssCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Server socket creation failed...");
                System.out.println("Retrying now...");
            }
        }while(!ssCreated);

        System.out.println("Server socket created...");

        // keep accepting new users
        while(true) {
            try {
                System.out.println("Waiting for client...");
                Socket soc = ss.accept();
                System.out.println("Connection established...");
                addUser(soc);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection error...");
            }
        }
    }

    public void addUser(Socket soc){ // adds a new user and assigns them a thread
        financeServerThread newUser = new financeServerThread(soc);
        fst.add(newUser);
        fst.get(connected).start();
        connected++;
    }

    public void removeUser(financeServerThread user){ // removes a user
        for(int i = 0; i < fst.size(); i++){
            if(fst.get(i) == user){
                fst.get(i).closeSocket();
                fst.remove(i);
                connected--;
            }
        }
    }
}
