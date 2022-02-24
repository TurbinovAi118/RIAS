package com.company.lab1;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientTCP {

    public static void main(String[] args) {
        try {
            //creating Socket object for connection with server
            Socket clientSocket = new Socket("localhost", 1500);

            //get a link to the thread associated with the server
            ObjectInputStream in =
                    new ObjectInputStream(clientSocket.getInputStream());

            //extracting object from input thread
            DateMessage dateMessage = (DateMessage) in.readObject();

            //output the data to the console
            System.out.println(dateMessage.getMessage());
            System.out.println(dateMessage.getDate());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
