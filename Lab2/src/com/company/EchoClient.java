package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    public static Socket socket;

    public static void main(String[] args) throws IOException {
        String logLevels = "ALL|DEBUG|INFO|WARN|ERROR|FATAL|OFF";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a command: ");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            String[] inputParameters = input.split(" ");
            switch (inputParameters[0].trim()) {
                case "connect":
                    connectToServer(inputParameters[1].trim(), inputParameters[2].trim());
                    break;
                case "send":
                    sendToServer(input.substring(inputParameters[0].trim().length()));
                    break;
                case "disconnect":
                    disconnectFromServer();
                    break;
                case "help":
                    printHelp();
                    break;
                case "quit":
                    System.out.println("Exit form app");
                    System.exit(0);
                    break;
                case "logLevel":
                    if (logLevels.contains(inputParameters[1].trim())){
                        System.out.println("logLevel has been set  as: " + inputParameters[1].trim());
                    }
                    else{
                        System.out.println("Wrong log level was selected, please try again");
                        printHelp();
                    }
                default:
                    printError();
                    printHelp();
            }
        }
    }

    public static void printError() {
        System.out.println("Error");
    }

    public static void printHelp() {
        System.out.println("connect\t Connects to existing server\t connect <ip> <port>");
        System.out.println("disconnect\t Disconnects client from server\t disconnect");
        System.out.println("send\t Sends message to server\t send <message>");
        System.out.println("logLevel\t Setting logger to existing logging level (ALL | DEBUG | INFO | WARN | ERROR| FATAL | OFF)\t logLevel <level>");
        System.out.println("help");
        System.out.println("quit\t Closing app\t quit");
    }

    public static void disconnectFromServer() throws IOException {
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
        System.out.println("Successfully disconnected the server");
    }

    public static void sendToServer(String message) {
        if (socket.isClosed()){
            System.out.println("Client is not connected to server");
        }
        else if (!socket.isClosed()){
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = null;
                line = message;
                out.println(line);
                out.flush();
                System.out.println("Server replied: " + in.readLine());
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void connectToServer(String ip, String port) throws IOException {
        socket = new Socket(ip, Integer.parseInt(port));
        System.out.println("Successfully connected to server with ip: " + ip + " and port: " + port);
    }
}
