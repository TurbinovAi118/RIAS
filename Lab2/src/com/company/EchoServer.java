package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(32003);
            server.setReuseAddress(true);
            Socket client = server.accept();

            System.out.println("New client connected " + client.getInetAddress().getHostAddress());
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.printf("Sent from the client: %s", line);
                out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
