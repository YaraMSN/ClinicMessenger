package com.company;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;


public class ClientHandler implements Runnable {
    private static HashMap<String, User> Database = new HashMap<>();
    private final Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private User MainUser;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            CloseEverything();
        }
    }

    public static void Load_Data() {
        try {
            FileInputStream fi = new FileInputStream("PickleRick.txt");
            ObjectInputStream fileoi = new ObjectInputStream(fi);
            Database = (HashMap<String, User>) fileoi.readObject();
            fileoi.close();
            System.out.println(Database.toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("PicleRick.txt isn't available");
        }
    }

    public void Export_Data() {
        try {
            FileOutputStream f = new FileOutputStream("PickleRick.txt");
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(Database);
            o.flush();
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CloseEverything() {
        try {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (socket != null) socket.close();
            Export_Data();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized boolean Register(User user) throws IOException {
        if (Database.containsKey(user.getUsername())) return false;

        Database.put(user.getUsername().toLowerCase(), user);
        return true;
    }

    private boolean Login(String input) {
        MainUser = null;
        String[] LoginInfo = input.substring(1).split(",");
        if (Database.containsKey(LoginInfo[0].toLowerCase()))
            if (Database.get(LoginInfo[0].toLowerCase()).getPassword().equals(LoginInfo[1])) {
                MainUser = Database.get(LoginInfo[0]);
                return true;
            }
        return false;
    }

    private synchronized boolean Edit(User two) {
        if (MainUser.getUsername().equalsIgnoreCase(two.getUsername())) {
            Database.replace(MainUser.getUsername(), two);
            Database.get(two.getUsername()).setInbox(MainUser.getInbox());
            Database.get(two.getUsername()).setSent_box(MainUser.getSent_box());
            return true;
        }
        if (Database.containsKey(two.getUsername())) return false;
        Database.remove(MainUser.getUsername());
        for (Message mess : MainUser.getSent_box())
            mess.setSender(two.getUsername());
        two.setSent_box(MainUser.getSent_box());

        for (Message mess : MainUser.getInbox()) {
            mess.setReceiver(two.getUsername());
        }
        two.setInbox(MainUser.getInbox());
        Database.put(two.getUsername(), two);
        return true;
    }

    @Override
    public void run() {
        String input;
        Gson gson = new Gson();
        while (socket.isConnected()) {
            try {
                input = (String) inputStream.readObject();
                switch (input.charAt(0)) {
                    case 'C': {
                        System.out.println(input.substring(1));
                        System.out.println(gson.fromJson(input.substring(1),User.class));
                        boolean did = Register(gson.fromJson(input.substring(1), User.class));
                        System.out.println("C :" + input + did);
                        outputStream.writeBoolean(did);
                        outputStream.flush();
                        break;
                    }
                    case 'L': {
                        System.out.println("L :" + input);
                        System.out.println(Login(input));
                        outputStream.reset();
                        outputStream.writeUnshared(MainUser);
                        System.out.println(MainUser);
                        outputStream.flush();
                        break;
                    }
                    case 'E': {
                        outputStream.writeBoolean(Edit(gson.fromJson(input.substring(1), User.class)));
                        outputStream.flush();
                        break;
                    }
                    case 'M': {
                        System.out.println("M :" + input);
                        Message message = gson.fromJson(input.substring(1), Message.class);
                        synchronized (MainUser.getUsername()) {
                            MainUser.addSentMessage(message);
                            if (Database.containsKey(message.getReceiver())) {
                                synchronized (Database.get(message.getReceiver())) {
                                    Database.get(message.getReceiver()).receiveMessage(message);
                                }
                            }
                        }
                        break;
                    }
                    case 'S': {
                        Message message = (Message) inputStream.readObject();
                        System.out.println("S :" + input);
                        System.out.println(message);
                        if (Database.containsKey(message.getSender().toLowerCase()))
                            synchronized (Database.get(message.getSender())) {
                                Database.get(message.getSender().toLowerCase()).SeenMassage(message);
                            }
                        if (Database.containsKey(message.getReceiver()))
                            synchronized (Database.get(message.getReceiver())) {
                                Database.get(message.getReceiver()).SeenMassage(message);
                            }
                        break;
                    }
                    case 'R': {
                        System.out.println("R : " + input);
                        Message message = gson.fromJson(input.substring(1), Message.class);
                        MainUser.removeMessage(message);
                        break;
                    }
                    case 'D': {
                        synchronized (Database) {
                            Database.remove(MainUser.getUsername());
                        }
                        break;
                    }
                    case 'N': {
                        System.out.println("N :" + MainUser);
                        MainUser = Database.get(MainUser.getUsername());
                        outputStream.reset();
                        outputStream.writeUnshared(MainUser);
                        outputStream.flush();
                        break;
                    }
                }
            } catch (IOException e) {
                CloseEverything();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
