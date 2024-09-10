package com.company;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        Server server=new Server(new ServerSocket(42024));
        server.startServer();
    }
}
