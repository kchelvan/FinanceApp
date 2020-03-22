package main.java.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class financeServer {
    private int port = 25565;

    private ServerSocket ss = null;

    private ArrayList<financeServerThread> fst = new ArrayList<financeServerThread>();

    private int connected = 0;
    public financeServer() {
        start();
    }

    /**
     * Starts the server and initializes everything
     * Waits for clients to connect and assigns them a new thread
     */
    public void start(){
        System.out.println("Starting server...");
        System.out.println("Creating server socket with port " + port + " ...");

        // keeps trying to crate a new Server Socket
        boolean ssCreated = false;
        do {
            try {
                ss = new ServerSocket(port);
                ss.setSoTimeout(0);
                ssCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Server socket creation failed...");
                System.out.println("Retrying now...");
            }
        }while(!ssCreated);

        System.out.println("Server socket created...");

        // keep accepting new users
        try {
            while (true) {
                System.out.println("Waiting for client...");
                try {
                    Socket soc = ss.accept();
                    System.out.println("Connection established...");
                    addUser(soc);
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    System.out.println("Connection error...");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Assigns a new user a thread
     *
     * @param soc The socket the user connects to
     * @throws IOException
     */
    public void addUser(Socket soc) throws IOException { // adds a new user and assigns them a thread
        financeServerThread newUser = new financeServerThread(soc, this);
        fst.add(newUser);
        fst.get(connected).start();
        connected++;
    }

    /**
     *Removes a user and closes the socket and thread
     *
     * @param user The user thread to be closed
     */
    public void removeUser(financeServerThread user){ // removes a user
        for(int i = 0; i < fst.size(); i++){
            if(fst.get(i) == user){
                fst.remove(i);
                connected--;

                System.out.println("Socket closed");
                System.out.println("User removed");
            }
        }
    }
}
