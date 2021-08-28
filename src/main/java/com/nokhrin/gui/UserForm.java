package com.nokhrin.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class UserForm extends JFrame {



    private JComboBox comboBox;
    private JComboBox comboBoxDistricts;
    private JComboBox comboBoxPositions;
    private JPanel panel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField salaryTextField;
    private JLabel markLabel;
    private JTable table;

    public UserForm() {
        this.setBounds(150, 200, 1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        init();
    }


    private void init() {

        panel = new JPanel(new GridLayout(1, 2, 10, 0));
        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        JPanel rightPanel = new JPanel(new GridLayout(8, 1, 0, 5));

        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        comboBox = new JComboBox(new String[]{"Employees", "Positions", "Districts"});
        comboBoxDistricts = new JComboBox(new String[]{"A", "B"});
        comboBoxPositions = new JComboBox(new String[]{"Programmer", "Tester"});

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        comboBox.setSelectedIndex(0);
        firstNameTextField =  new JTextField("");
        lastNameTextField = new JTextField("");
        salaryTextField = new JTextField("");
        salaryTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
        markLabel = new JLabel("Salary");
        leftPanel.add(comboBox, BorderLayout.NORTH);
        leftPanel.add(sp, BorderLayout.CENTER);
        JPanel lowerLeftPanel = new JPanel(new GridLayout(1, 3, 5, 0));

        lowerLeftPanel.add(markLabel);
        lowerLeftPanel.add(createButton("Show avg salary", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        }));
        lowerLeftPanel.add(createButton("Employee info", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }

        }));
        leftPanel.add(lowerLeftPanel, BorderLayout.SOUTH);

        rightPanel.add(new JLabel("First name:"));
        rightPanel.add(firstNameTextField);
        rightPanel.add(new JLabel("Last name:"));
        rightPanel.add(lastNameTextField);
        JPanel listsPanel = new JPanel(new GridLayout(2, 2, 5, 0));
        listsPanel.add(new JLabel("Select district:"));
        listsPanel.add(new JLabel("Select position:"));
        listsPanel.add(comboBoxDistricts);
        listsPanel.add(comboBoxPositions);
        rightPanel.add(listsPanel);
        rightPanel.add(new JLabel("Salary:"));
        rightPanel.add(salaryTextField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));

        buttonPanel.add(createButton("Add employee", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }

        }));

        buttonPanel.add(createButton("Remove employee", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }

        }));
        JPanel secondaryButtonPanel = new JPanel(new GridLayout(2, 1, 0, 10));

        secondaryButtonPanel.add(createButton("Add district", new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        }));
        secondaryButtonPanel.add(createButton("Add position", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        }));

        buttonPanel.add(secondaryButtonPanel);
        rightPanel.add(buttonPanel);

        panel.add(leftPanel);
        panel.add(rightPanel);
        add(panel);
    }


    private JButton createButton(String name, ActionListener listener) {
        JButton button = new JButton(name);

        button.setEnabled(true);
        button.addActionListener(listener);
        return button;
    }

}
