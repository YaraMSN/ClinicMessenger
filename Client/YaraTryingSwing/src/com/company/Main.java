package com.company;

import java.io.IOException;
import java.net.Socket;

public class Main {
    // private String title;
    // private Panel panel;
    // public Main(String title,JPanel panel){
    //    add(panel);
    //    setTitle(title);
    //    setLocationRelativeTo(null);
    //    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    setVisible(true);
    //    pack();
    //}

    public static void main(String[] args) throws IOException {
        //new SignUpMenu();

        Socket socket = new Socket("localhost", 42024);
        ServerHandler serverHandler = new ServerHandler(socket);
        new MainMenu();

        // write your code here
        //MyMenu menu=new MyMenu();
        //ImageIcon image=new ImageIcon("logo.png");
        //JFrame frame=new JFrame("Yara");
        //frame.setSize(420,420);
        //frame.setLocationRelativeTo(null);
        //frame.setResizable(false);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setVisible(true);
        //frame.setIconImage(image.getImage());
        //ImageIcon image=new ImageIcon("logo.ico");
        //frame.getContentPane().setBackground(Color.white);
    }

}
