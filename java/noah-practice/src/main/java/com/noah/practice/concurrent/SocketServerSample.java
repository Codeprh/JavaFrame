package com.noah.practice.concurrent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServerSample {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("等待客户端连接.");
        Socket socket = serverSocket.accept();
        System.out.println("有客户端连接上了 " + socket.getInetAddress() + ":" + socket.getPort() + "");

        OutputStream outputStream = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入指令: ");
            String s = scanner.nextLine();
            String message = s + "\n";
            outputStream.write(message.getBytes("US-ASCII"));
        }
    }
}
