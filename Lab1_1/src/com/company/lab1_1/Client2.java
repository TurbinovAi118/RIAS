package com.company.lab1_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) throws IOException {
        System.out.println("Пожалуйста, введите ник");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();

        Socket client = new Socket("localhost",7777);
        // Клиент отправляет
        new Thread(new Send(client,name)).start();
        // Клиент получает
        new Thread(new Receive(client)).start();
    }

}