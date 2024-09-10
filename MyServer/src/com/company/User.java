package com.company;

import java.io.Serializable;
import java.util.Vector;

public class User implements Serializable {
    private final String name;
    private final String username;
    private final String password;
    private final int job;
    private Vector<Message> Sent_box = new Vector<>();
    private Vector<Message> Inbox = new Vector<>();

    public User(String name, String username, String password, int job) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.job = job;
    }


    public String getPassword() {
        return password;
    }

    public int getJob() {
        return job;
    }

    public void SeenMassage(Message message) {
        for (Message mess : Inbox)
            if (mess.equals(message))
                mess.Seen();
        for (Message mess : Sent_box)
            if (mess.equals(message))
                mess.Seen();
    }

    public void addSentMessage(Message message) {
        Sent_box.add(message);
    }

    public void receiveMessage(Message message) {
        Inbox.add(message);
    }

    public void removeMessage(Message message) {
        Sent_box.remove(message);
        Inbox.remove(message);
    }

    public String getName() {
        return name;
    }

    public Vector<Message> getSent_box() {
        return Sent_box;
    }

    public void setSent_box(Vector<Message> sent_box) {
        Sent_box = sent_box;
    }

    public Vector<Message> getInbox() {
        return Inbox;
    }

    public void setInbox(Vector<Message> inbox) {
        Inbox = inbox;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", job=" + job +
                ", Sent_box=" + Sent_box +
                ", Inbox=" + Inbox +
                '}';
    }
}
