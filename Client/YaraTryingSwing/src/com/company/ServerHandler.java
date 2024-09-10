package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerHandler {
    public static Socket socket;
    public static ObjectInputStream ip;
    public static ObjectOutputStream op;

    public ServerHandler(Socket socket) {
        ServerHandler.socket = socket;
        try {
            ip = new ObjectInputStream(socket.getInputStream());
            op = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            CloseEverything();
        }
    }

    public static void CloseEverything() {
        try {
            if (ip != null) ip.close();
            if (op != null) op.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
