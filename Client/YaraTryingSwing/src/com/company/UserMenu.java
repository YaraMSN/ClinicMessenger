package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class UserMenu extends JFrame implements ActionListener {
    private final JButton edit = new JButton("Edit Profile");
    private final JButton send = new JButton("Send email");
    private final JButton sent = new JButton("Sent");
    private final JButton inbox = new JButton("Inbox");
    private final JMenuBar bar = new JMenuBar();
    private final JMenuItem exit = new JMenuItem("Sign out");
    private final JMenuItem delete = new JMenuItem("delete account");
    ImageIcon image = new ImageIcon("logo2.png");
    private User user;

    public UserMenu(User user) {
        //System.out.println("I am alive");
        setJMenuBar(bar);
        JMenu menu = new JMenu("Spcial things");
        bar.add(menu);
        menu.add(exit);
        menu.add(delete);
        exit.addActionListener(this);
        delete.addActionListener(this);
        this.user = user;

        JLabel label = new JLabel((user.getJob() == 1 ? "Dr." : "Pt.") + user.getName() + "'s panel :");
        label.setBounds(20, 5, 200, 30);
        label.setFont(new Font(null, Font.BOLD, 20));

        edit.setBounds(83, 60, 224, 60);
        edit.setFont(new Font(null, Font.PLAIN, 25));
        edit.addActionListener(this);
        edit.setFocusable(false);

        send.setBounds(83, 130, 224, 60);
        send.setFont(new Font(null, Font.PLAIN, 25));
        send.addActionListener(this);
        send.setFocusable(false);

        sent.setBounds(83, 200, 224, 60);
        sent.setFont(new Font(null, Font.PLAIN, 25));
        sent.addActionListener(this);
        sent.setFocusable(false);

        inbox.setBounds(83, 270, 224, 60);
        inbox.setFont(new Font(null, Font.PLAIN, 25));
        inbox.addActionListener(this);
        inbox.setFocusable(false);

        add(label);
        add(edit);
        add(send);
        add(sent);
        add(inbox);


        setIconImage(image.getImage());
        setTitle("Menu");
        setLayout(null);
        setSize(390, 470);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edit) {
            new SignUpMenu(user, true, this);
            dispose();
            new MainMenu();
        }
        if (e.getSource() == send) {
            new SendMenu(this, user);
        }
        if (e.getSource() == sent) {
            try {
                ServerHandler.op.writeObject("N");
                user = (User) ServerHandler.ip.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                ServerHandler.CloseEverything();
            }
            new Table(this, user, "Sent");
        }
        if (e.getSource() == inbox) {
            try {
                ServerHandler.op.writeObject("N");
                user = (User) ServerHandler.ip.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                ServerHandler.CloseEverything();
            }
            new Table(this, user, "Inbox");
        }
        if (e.getSource() == exit) {
            dispose();
            new MainMenu();

        }
        if (e.getSource() == delete) {
            int x = JOptionPane.showConfirmDialog(delete, "Are you sure? this action is irreversible!", "Deleting account", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {
                dispose();
                new MainMenu();
                try {
                    ServerHandler.op.writeObject("D");
                    ServerHandler.op.flush();
                } catch (IOException ex) {
                    ServerHandler.CloseEverything();
                }
            }
        }
    }
}












