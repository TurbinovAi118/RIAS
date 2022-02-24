package com.company.lab1;

import java.net.*;

public class Client {
    private static MulticastSocket socket;
    private static InetAddress address;
    private static byte[] buffer;
    private static DatagramPacket packet;
    private static String str;

    public static void main(String[] args) throws Exception {

        System.out.println("Waiting for server's message");
        try {
            //creating object MulticastSocket, to get data
            //from group with using port 1502
            socket = new MulticastSocket(1502);

            address = InetAddress.getByName("233.0.0.1");

            socket.joinGroup(address);

            while (true){
                buffer = new byte[256];
                packet = new DatagramPacket(buffer, buffer.length);
                //getting data from server
                socket.receive(packet);
                str = new String(packet.getData());
                System.out.println("New message is: " + str.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                socket.leaveGroup(address);

                socket.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}