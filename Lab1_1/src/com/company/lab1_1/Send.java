package com.company.lab1_1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Send implements Runnable{
    // Консольный поток ввода
    private BufferedReader console;
    // Конвейерный поток
    private DataOutputStream dos;
    // Управляющий поток
    private boolean flag= true;
    // чат ник
    private String name;

    public Send() {
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public Send(Socket client, String name){
        this();
        this.name = name;
        try {
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            //e.printStackTrace();
            flag = false;
        }

    }
    // Получаем данные из консоли
    private String getMsgFromConsole() {
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // отправить данные
    public void send(String msg){
        if (null != msg && !msg.equals("")){
            try {
                dos.writeUTF(name +": " + msg);
                Date now = new Date(System.currentTimeMillis());
                String time = new SimpleDateFormat("hh:mm:ss   yyyy/MM/dd  ").format(now);
                System.out.println(time);
                System.out.println(name + ":" + msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    flag = false;
                    dos.close();
                    console.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }else{
            try {
                dos.writeUTF(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        // Тело потока
        send("");
        while (flag){
            send(getMsgFromConsole());
        }
    }
}