package main.java.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class financeServerThread extends Thread {
    private Socket soc;

    private DataInputStream fromClient;
    private DataOutputStream toClient;

    financeServerThread(Socket soc) throws IOException {
        System.out.println("Starting user thread...");

        this.soc = soc;

        fromClient = new DataInputStream(soc.getInputStream());
        toClient = new DataOutputStream(soc.getOutputStream());
    }

    public void run() {
        System.out.println("User thread running...");
    }

    public void closeSocket(){ // closes the socket
        System.out.println("Closing socket...");
        try{
            System.out.println("Socket closed...");
            soc.close();
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Socket failed to close...");
        }
    }
}
