package main.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class financeClient {
    private Socket soc;

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    public financeClient() throws IOException {
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

        fromServer = new DataInputStream(soc.getInputStream());
        toServer = new DataOutputStream(soc.getOutputStream());
    }
}
