package com.company;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.util.Vector;

public class MessageTableModel extends AbstractTableModel {
    private Vector<Message> messages;

    public MessageTableModel(User user, String title) {
        if (title.equalsIgnoreCase("sent")) messages = user.getSent_box();
        else messages = user.getInbox();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Sender";
            case 1:
                return "Receiver";
            case 2:
                return "Mail Title";
            case 3:
                return "date";
            case 4:
                return "seen";
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return messages.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Message message = messages.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return message.getSender();
            case 1:
                return message.getReceiver();
            case 2:
                return message.getTitle();
            case 3:
                return message.getDate();
            case 4:
                return message.isSeen();
        }
        return null;
    }

    public void Delete_message(int x) {
        Gson gson = new Gson();
        String s = "R" + gson.toJson(messages.get(x));
        try {
        ServerHandler.op.writeObject(s);
        ServerHandler.op.flush();
        messages.remove(x);
        } catch (IOException e) {
            ServerHandler.CloseEverything();
        }
    }

    public void Show_message(int x, String title) {
        Message message = messages.get(x);
        if (title.equals("Inbox")) {
            try {
                ServerHandler.op.writeObject("S");
                ServerHandler.op.flush();
                ServerHandler.op.writeObject(message);
                ServerHandler.op.flush();
            } catch (IOException e) {
                ServerHandler.CloseEverything();
            }
            message.Seen();
        }
        JOptionPane.showMessageDialog(null, message.getContent(), message.getTitle(), JOptionPane.PLAIN_MESSAGE);
    }
}
