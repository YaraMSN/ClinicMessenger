package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static java.lang.System.gc;

public class MainMenu extends JFrame implements ActionListener {
    private final JButton Up = new JButton("Sign up");
    private final JButton In = new JButton("Sign in");

    public MainMenu() {
        gc();
        ImageIcon image = new ImageIcon("logo2.png");
        ImageIcon image2 = new ImageIcon("128.png");
        JLabel Lie = new JLabel(image2);
        Lie.setBounds(17, 17, 250, 250);
        add(Lie);
        setIconImage(image.getImage());
        Up.setBounds(17, 250, 250, 85);
        In.setBounds(17, 350, 250, 85);
        Up.setFocusable(false);
        In.setFocusable(false);
        Up.setFont(new Font(null, Font.PLAIN, 20));
        In.setFont(new Font(null, Font.PLAIN, 20));
        Up.addActionListener(this);
        In.addActionListener(this);

        add(Up);
        add(In);
        setTitle("Menu");
        setSize(300, 500);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Up) {
            new SignUpMenu(new User("", "", "", 1), false, this);
        }

        if (e.getSource() == In) {
            JTextField userField = new JTextField(10);
            JPasswordField passwordField = new JPasswordField(10);
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("username:"));
            myPanel.add(userField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("password:"));
            myPanel.add(passwordField);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter your username and password :", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String username = userField.getText().toLowerCase();
                String pass = String.valueOf(passwordField.getPassword());
                if (username.equals("") || pass.equals(""))
                    JOptionPane.showMessageDialog(this, "Please full fill all blanks", "Information", JOptionPane.ERROR_MESSAGE);
                else try {
                    ServerHandler.op.writeObject("L" + username + "," + pass);
                    ServerHandler.op.flush();
                    User what = (User) ServerHandler.ip.readObject();
                    if (what == null)
                        JOptionPane.showMessageDialog(this, "Wrong username or password", "login failed", JOptionPane.ERROR_MESSAGE);
                    else {
                        System.out.println("lets open this");
                        dispose();
                        new UserMenu((User) what);
                        System.out.println((User) what);
                    }
                } catch (IOException ex) {
                    ServerHandler.CloseEverything();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }
}
