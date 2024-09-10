package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Table extends JDialog {
    private final JPopupMenu popupMenu = new JPopupMenu();


    public Table(Frame owner, User user, String title) {
        super(owner, title, true);
        MessageTableModel tableModel = new MessageTableModel(user,title);
        ImageIcon image = new ImageIcon("logo2.png");
        setIconImage(image.getImage());
        JTable table = new JTable(tableModel);
        JMenuItem delete = new JMenuItem("delete message");
        popupMenu.add(delete);
        JMenuItem read = new JMenuItem("read message");
        popupMenu.add(read);
        table.setShowHorizontalLines(true);
        table.setAutoCreateRowSorter(true);
        table.setDragEnabled(false);
        table.setRowHeight(30);
        table.setFont(new Font(null, Font.PLAIN, 17));
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
        delete.addActionListener(e -> {
            int x = table.getSelectedRow();
            tableModel.Delete_message(x);
            table.setVisible(false);
            table.setVisible(true);
        });
        read.addActionListener(e -> {
            int x = table.getSelectedRow();
            tableModel.Show_message(x, title);
            table.setVisible(false);
            table.setVisible(true);
        });

        add(new JScrollPane(table));
        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
