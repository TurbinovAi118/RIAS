package com.company.lab1_1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable{
    // Входной поток
    private DataInputStream dis;
    // Идентификация потока для определения текущего состояния потока
    private boolean flag = true;


    public Receive(Socket client){
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            try {
                dis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    // Получаем данные
    public String receive(){
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            try {
                dis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return msg;
    }

    @Override
    public void run() {
        while(flag){
            System.out.println(receive());
        }
    }
}