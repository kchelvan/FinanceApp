package main.java.server;

import java.io.IOException;
import java.net.Socket;

public class financeServerThread extends Thread {
    private Socket soc;

    financeServerThread(Socket soc){
        System.out.println("Starting user thread...");

        this.soc = soc;
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
