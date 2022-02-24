package com.company.lab1;

import java.io.*;
import java.net.*;

public class Server {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private BufferedReader in = null;
    private String str = null;
    private byte[] buffer;
    private InetAddress address;


    public Server() throws IOException {
        System.out.println("Sending message");
        socket = new DatagramSocket();


        transmit();

    }

    public void transmit() {
        try {
            in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Enter data to send to clients: ");

                str = in.readLine();
                buffer = str.getBytes();
                address = InetAddress.getByName("233.0.0.1");
                //sending pocket to port 1502
                packet = new DatagramPacket(
                        buffer,
                        buffer.length,
                        address,
                        1502);

                socket.send(packet);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                in.close();
                socket.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Server();
    }
}