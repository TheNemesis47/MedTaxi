package com.example.medtaxi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class BookingPanel extends JFrame {

    private JLabel fromLabel;
    private JTextField fromTextField;
    private JLabel toLabel;
    private JTextField toTextField;
    private JLabel dateLabel;
    private JTextField dateTextField;
    private JLabel timeLabel;
    private JTextField timeTextField;
    private JLabel companyLabel;
    private JComboBox<String> companyComboBox;
    private JButton bookButton;

    public BookingPanel() {
        super("Prenota un trasferimento");

        fromLabel = new JLabel("Da:");
        fromTextField = new JTextField();
        toLabel = new JLabel("A:");
        toTextField = new JTextField();
        dateLabel = new JLabel("Data:");
        dateTextField = new JTextField();
        timeLabel = new JLabel("Ora:");
        timeTextField = new JTextField();
        companyLabel = new JLabel("Azienda:");
        companyComboBox = new JComboBox<>();
        bookButton = new JButton("Prenota");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(fromLabel);
        topPanel.add(fromTextField);
        topPanel.add(toLabel);
        topPanel.add(toTextField);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2));
        centerPanel.add(dateLabel);
        centerPanel.add(dateTextField);
        centerPanel.add(timeLabel);
        centerPanel.add(timeTextField);
        centerPanel.add(companyLabel);
        companyComboBox.addItem("Azienda 1");
        companyComboBox.addItem("Azienda 2");
        centerPanel.add(companyComboBox);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(bookButton);
        add(bottomPanel, BorderLayout.SOUTH);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Prenotazione effettuata");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}