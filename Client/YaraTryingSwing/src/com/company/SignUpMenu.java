package com.company;

import com.google.gson.Gson;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class SignUpMenu extends JDialog implements ActionListener {
    private final JLabel passLabel2 = new JLabel("Confirm Password : ");
    private final JTextField namefield;
    private final JTextField userfield;
    private final JPasswordField passfield;
    private final JPasswordField passfield2;
    private final JRadioButton radioButton1 = new JRadioButton("Doctor");
    private final JRadioButton radioButton2 = new JRadioButton("Patient");
    private final JButton submit = new JButton("Submit");
    private boolean isEditing;
    private User user;

    public SignUpMenu(User user, boolean isEditing, JFrame f) {

        super(f, "test", true);
        ImageIcon image = new ImageIcon("logo2.png");
        setIconImage(image.getImage());
        this.user = user;
        this.isEditing = isEditing;
        namefield = new JTextField(user.getName());
        userfield = new JTextField(user.getUsername());
        passfield = new JPasswordField(user.getPassword());
        passfield2 = new JPasswordField(user.getPassword());
        if (user.getJob() == 1) radioButton1.setSelected(true);
        else if (user.getJob() == 2) radioButton2.setSelected(true);

        JLabel nameLabel = new JLabel("Fullname : ");
        nameLabel.setBounds(40, 30, 110, 25);
        nameLabel.setFont(new Font(null, Font.BOLD, 20));
        namefield.setBounds(150, 30, 200, 30);
        namefield.setFont(new Font(null, Font.PLAIN, 19));

        JLabel userLabel = new JLabel("Username : ");
        userLabel.setBounds(35, 80, 115, 25);
        userLabel.setFont(new Font(null, Font.BOLD, 20));
        userfield.setBounds(150, 80, 200, 30);
        userfield.setFont(new Font(null, Font.PLAIN, 19));

        JLabel passLabel = new JLabel("Password : ");
        passLabel.setBounds(35, 130, 115, 25);
        passLabel.setFont(new Font(null, Font.BOLD, 20));
        passfield.setBounds(150, 130, 200, 30);
        passfield.setFont(new Font(null, Font.PLAIN, 19));

        passLabel2.setBounds(35, 180, 200, 25);
        passLabel2.setFont(new Font(null, Font.BOLD, 20));
        passfield2.setBounds(35, 230, 315, 30);
        passfield2.setFont(new Font(null, Font.PLAIN, 19));

        JLabel role = new JLabel("Role :");
        role.setBounds(35, 290, 100, 30);
        role.setFont(new Font(null, Font.BOLD, 19));
        radioButton1.setBounds(125, 290, 100, 30);
        radioButton1.setFont(new Font(null, Font.PLAIN, 19));
        radioButton2.setBounds(230, 290, 100, 30);
        radioButton2.setFont(new Font(null, Font.PLAIN, 19));
        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);

        submit.setBounds(35, 350, 150, 50);
        submit.addActionListener(this);
        submit.setFocusable(false);
        JButton resetbutton = new JButton("Reset");
        resetbutton.setBounds(200, 350, 150, 50);
        resetbutton.addActionListener(e -> {
            namefield.setText("");
            userfield.setText("");
            passfield.setText("");
            passfield2.setText("");
        });
        resetbutton.setFocusable(false);

        add(nameLabel);
        add(namefield);
        add(userLabel);
        add(userfield);
        add(passLabel);
        add(passfield);
        add(passLabel2);
        add(passfield2);
        add(role);
        add(radioButton1);
        add(radioButton2);
        add(submit);
        add(resetbutton);

        if (isEditing) setTitle("Edit profile");
        else setTitle("Sign up");
        setLayout(null);
        setSize(390, 470);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private boolean IsAllFilled() {
        if (namefield.getText().isEmpty()) return false;
        if (userfield.getText().isEmpty()) return false;
        if (String.valueOf(passfield.getPassword()).isEmpty()) return false;
        return radioButton1.isSelected() || radioButton2.isSelected();
    }

    private User collectData() {
        if (!String.valueOf(passfield.getPassword()).equals(String.valueOf(passfield2.getPassword())))
            JOptionPane.showMessageDialog(passLabel2, "Passwords doesnt match", "Confirm your password", JOptionPane.ERROR_MESSAGE);
        else if (!IsAllFilled())
            JOptionPane.showMessageDialog(this, "Please full fill all blanks", "Information", JOptionPane.ERROR_MESSAGE);
        else {
            Gson gson = new Gson();
            return new User(namefield.getText().toLowerCase(), userfield.getText().toLowerCase(), new String(passfield.getPassword()), (radioButton1.isSelected() ? 1 : 2));
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {

            User cuser = collectData();
            if (cuser != null) {
                Gson gson = new Gson();
                String s;
                if (isEditing) s="E";

                else s = "C";
                System.out.println(cuser);
                System.out.println(gson.toJson(cuser));
                s+= gson.toJson(cuser);
                try {
                    ServerHandler.op.writeObject(s);
                    ServerHandler.op.flush();
                    if (ServerHandler.ip.readBoolean()) {
                        JOptionPane.showMessageDialog(this, "successful", "ClinicChat", JOptionPane.PLAIN_MESSAGE);
                        dispose();
                    } else
                        JOptionPane.showMessageDialog(this, "wasnt successful,change username", "Failed", JOptionPane.ERROR_MESSAGE);//Todo:check this
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Sign up failed", "Failed", JOptionPane.ERROR_MESSAGE);
                    ServerHandler.CloseEverything();
                }
            }

        }
    }
}

