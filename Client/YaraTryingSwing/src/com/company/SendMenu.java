package com.company;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SendMenu extends JDialog implements ActionListener {
    private final User user;
    private final JLabel to = new JLabel("To:");
    private final JLabel title = new JLabel("Title:");
    private final JTextField receviers = new JTextField();
    private final JTextField emailTitle = new JTextField();
    private final JTextArea message = new JTextArea();
    private final JButton submit = new JButton("Submit");

    public SendMenu(JFrame f, User user) {
        super(f, "Sending mail", true);
        this.user = user;
        ImageIcon image = new ImageIcon("logo2.png");
        setIconImage(image.getImage());

        to.setBounds(0, 0, 50, 50);
        to.setFont(new Font(null, Font.BOLD, 19));
        receviers.setBounds(50, 0, 450, 50);
        receviers.setFont(new Font(null, Font.PLAIN, 19));

        title.setBounds(0, 48, 50, 50);
        title.setFont(new Font(null, Font.BOLD, 19));
        emailTitle.setBounds(50, 48, 450, 50);
        emailTitle.setFont(new Font(null, Font.PLAIN, 19));

        message.setBounds(0, 100, 500, 380);
        message.setFont(new Font(null, Font.PLAIN, 15));

        submit.setBounds(138, 490, 224, 60);
        submit.setFont(new Font(null, Font.PLAIN, 25));
        submit.setFocusable(false);
        submit.addActionListener(this);

        add(to);
        add(title);
        add(receviers);
        add(emailTitle);
        add(message);
        add(submit);

        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            if (receviers.getText().equals("") || title.getText().equals("") || message.getText().equals(""))
                System.out.println("Something wrong");
            else {
                try {
                    Gson gson = new Gson();
                    Message mail;
                    String[] res = receviers.getText().split(";");
                    for (String re : res) {
                        mail = new Message(user.getUsername().toLowerCase(), re, emailTitle.getText(), message.getText());
                        String s = "M" + gson.toJson(mail);
                        ServerHandler.op.writeObject(s);
                        ServerHandler.op.flush();
                        dispose();
                    }
                } catch (IOException ex) {
                    ServerHandler.CloseEverything();
                }
            }

        }
    }
}
