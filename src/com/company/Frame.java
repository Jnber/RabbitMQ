package com.company;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.util.ArrayList;

public class Frame extends JFrame implements CaretListener {
    private int num;
    private User sender;
    private String prevMsg;
    private JLabel label;
    public static JPanel panel ;
    private JTextArea textarea;
    private int size;
    public static ArrayList<JLabel> senders_labels;

    public Frame(int num, User sender, int size) {
        this.num = num;
        this.sender = sender;
        this.prevMsg = "";
        this.label = new JLabel();
        this.panel= new JPanel();
        this.textarea = new JTextArea();
        this.size= size;
        senders_labels = new ArrayList<JLabel>();

        panel.setPreferredSize(new Dimension(100,100));
        panel.setLayout(new FlowLayout(FlowLayout.TRAILING,10,2));

        for (int i=0; i<size; i++){

                senders_labels.add(new JLabel());
                senders_labels.get(i).setPreferredSize(new Dimension(70, 20));
                senders_labels.get(i).setBackground(Color.darkGray);
                senders_labels.get(i).setOpaque(true);
                senders_labels.get(i).setForeground(Color.lightGray);
                senders_labels.get(i).setFont(new Font(Font.SANS_SERIF,Font.BOLD,10));
                senders_labels.get(i).setText("user "+i);
                senders_labels.get(i).setHorizontalAlignment(JLabel.CENTER);
                senders_labels.get(i).setVisible(true);
                senders_labels.get(i).setVerticalAlignment(JLabel.CENTER);
                panel.add(senders_labels.get(i));

        }

        label.setOpaque(true);
        label.setText("Document");
        label.setBackground(new Color(0xff1251));
        label.setForeground(Color.darkGray);

        textarea.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,15));
        textarea.setLineWrap(true);
        textarea.setCaretColor(Color.white);
        textarea.setPreferredSize(new Dimension(100,480));
        textarea.setBackground(Color.DARK_GRAY);
        textarea.setForeground(Color.lightGray);
        textarea.addCaretListener(this);

        this.setSize(420, 600);
        this.setTitle("Sender" + num);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout(0,10));
        this.setVisible(true);
        this.add(label, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.add(textarea, BorderLayout.SOUTH);
    }

    @Override
    public void caretUpdate(CaretEvent e) {

        String message = textarea.getText();
        //only send message if it s diff from the previous one
        if (!message.equals(prevMsg)){
            try {
                this.sender.send(message);
                prevMsg = message;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void write(String message, String bindingKey){

        int index = Integer.valueOf(bindingKey);
        textarea.setText(message);

    }
}
