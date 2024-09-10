package com.company;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Serializable {

    private final String Title;
    private final String Content;
    private final LocalDateTime Date;
    private String Sender;
    private String Receiver;
    private boolean Seen;

    public Message(String sender, String receiver, String title, String content) {
        Sender = sender;
        Receiver = receiver;
        Title = title;
        Content = content;
        Date = LocalDateTime.now();
        Seen = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Seen == message.Seen && Title.equals(message.Title) && Content.equals(message.Content) && Date.equals(message.Date) && Sender.equals(message.Sender) && Receiver.equals(message.Receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Title, Content, Date, Sender, Receiver, Seen);
    }

    @Override
    public String toString() {
        return "Message{" +
                "Sender='" + Sender + '\'' +
                ", Receiver='" + Receiver + '\'' +
                ", Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                ", Date=" + Date +
                ", Seen=" + Seen +
                '}';
    }

    public void Seen() {
        Seen = true;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public boolean isSeen() {
        return Seen;
    }
}
