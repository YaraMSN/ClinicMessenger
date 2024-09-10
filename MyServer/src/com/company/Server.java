package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void startServer() throws IOException {
        try
        {
            ClientHandler.Load_Data();
            while (!serverSocket.isClosed())
            {
                Socket socket=serverSocket.accept();
                System.out.println("someone connected to the server");
                ClientHandler clientHandler=new ClientHandler(socket);
                Thread thread=new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            if (!serverSocket.isClosed())
                serverSocket.close();
            e.printStackTrace();
        }
    }
}
