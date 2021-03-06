package com.company.lab1_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    private List<MyChannel> all = new ArrayList<MyChannel>();

    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(7777);
        while(true) {
            Socket client = server.accept();
            MyChannel channel = new MyChannel(client);
            all.add(channel);
            new Thread(channel).start();        // Дорога
        }
    }

    /**
     * Один клиент на дорогу
     * Установить канал данных между сервером и клиентом
     *
     */
    private  class MyChannel implements Runnable {

        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean flag = true;
        private String name;

        public MyChannel(Socket client) {
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                flag = false;

                try {
                    dis.close();
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        // Получение информации о клиенте
        private String receive() {
            String msg = "";
            try {
                msg = dis.readUTF();
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
                all.remove(this);
                try {
                    dis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return msg;
        }

        // Отправить информацию клиенту
        private void send(String msg) {
            if (null == msg || msg.equals("")) {
                return;
            }
            try {
                dos.writeUTF(time());
                dos.writeUTF(msg);
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
                all.remove(this);  // удалить себя
                try {
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        private void sendOthers(String msg) {
            // Определяем, является ли это приватным чатом
            if (msg.contains("@") && msg.indexOf("：") > msg.indexOf("@")){
                String spot = null;

                String secreName = msg.substring(msg.indexOf("@") + 1, msg.indexOf("："));
                String secretMsg = msg.substring(msg.indexOf("：") + 1);


                for (MyChannel other : all) {
                    if (secreName.equals(other.name)) {
                        other.send(name + "Скажу тихо:" + secretMsg);
                    }

                }
            }else{

                for (MyChannel other : all) {
                    if (other == this) {
                        continue;
                    }
                    other.send(msg);
                }
            }
        }


        private String time () {
            Date now = new Date(System.currentTimeMillis());
            String time = new SimpleDateFormat("yyyy.MM.dd  hh:mm:ss").format(now);
            return time;
        }

        @Override
        public void run () {
            send("Добро пожаловать в групповой чат");
            name = receive();
            sendOthers(name + "Регистрация в групповом чате");
            while (flag) {
                sendOthers(receive());
            }
        }
    }
}