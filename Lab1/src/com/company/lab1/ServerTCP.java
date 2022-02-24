package com.company.lab1;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

//server class
public class ServerTCP extends Thread {

    //link to object - server socket
    ServerSocket serverSocket = null;

    //default constructor
    public ServerTCP(){
        try {
            //creating object ServerSocket, which gets client requests to port 1500
            serverSocket = new ServerSocket(1500);
            System.out.println("Starting the serer ");
            //process startup
            start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //process startup
    public void run() {
        try {
            while(true){
                //waiting for connection requests from clients
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted from " + clientSocket.getInetAddress().getHostAddress());

                //getting output stream
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                //creating object for transfer to clients
                DateMessage dateMessage = new DateMessage(
                        Calendar.getInstance().getTime(), "Current date/time on server");

                //write the object for transfer to clients
                out.writeObject(dateMessage);
                out.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        new ServerTCP();
    }
}
