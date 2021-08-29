package com.nokhrin.gui;


import com.nokhrin.controller.Controller;


import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class UserForm extends JFrame {


    private Controller controller;
    private JComboBox comboBox;
    private JComboBox comboBoxDistricts;
    private JComboBox comboBoxPositions;
    private JPanel panel;
    private JButton removeButton;
    private JButton avgButton;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField salaryTextField;
    private JLabel markLabel;
    private final Font TEXT_FONT = new Font("Serif", Font.BOLD, 16);
    private JTable table;

    public UserForm() {
        this.setBounds(150, 200, 1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        controller = new Controller();
        UIManager.put("Button.font", new FontUIResource(TEXT_FONT));
        init();
    }


    private void init() {

        panel = new JPanel(new GridLayout(1, 2, 10, 0));
        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        JPanel rightPanel = new JPanel(new GridLayout(8, 1, 0, 5));

        table = (JTable) setFontToComponent(new JTable());
        JScrollPane sp = new JScrollPane(table);
        comboBox = (JComboBox) setFontToComponent(new JComboBox(new String[]{"Employees", "Positions", "Districts"}));
        comboBoxDistricts = (JComboBox) setFontToComponent(new JComboBox(controller.getDataListForComboBox(2)));
        comboBoxPositions = (JComboBox) setFontToComponent(new JComboBox(controller.getDataListForComboBox(1)));
        avgButton = createButton("Show avg salary", e -> {

            markLabel.setText(controller.getAvgSalary(getIdTableValue()));
            table.setModel(new DefaultTableModel(controller.getTableDataWithSalary(), new String[]{"ID", "Name", "District", "Salary",}));
            avgButton.setEnabled(false);
        });
        removeButton = createButton("Remove employee", e -> {
            int selectedIndex = comboBox.getSelectedIndex();

            controller.deleteEmployee(selectedIndex, getIdTableValue());
            comboBox.setSelectedIndex(selectedIndex);
        });
        comboBox.addActionListener(e -> {
            int selectedIndex = comboBox.getSelectedIndex();
            removeButton.setEnabled(controller.isRemoveButtonEnabled(selectedIndex));
            table.setModel(new DefaultTableModel(controller.getDataListForTable(selectedIndex), new String[]{"ID", "Name"}));
            avgButton.setEnabled((controller.isRemoveAvgButton(selectedIndex, table.getRowCount())));
        });
        comboBox.setSelectedIndex(0);
        firstNameTextField = (JTextField) setFontToComponent(new JTextField(""));
        lastNameTextField = (JTextField) setFontToComponent(new JTextField(""));
        salaryTextField = (JTextField) setFontToComponent(new JTextField(""));
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
        markLabel = (JLabel) setFontToComponent(new JLabel("Salary"));
        leftPanel.add(comboBox, BorderLayout.NORTH);
        leftPanel.add(sp, BorderLayout.CENTER);
        JPanel lowerLeftPanel = new JPanel(new GridLayout(1, 3, 5, 0));

        lowerLeftPanel.add(markLabel);
        lowerLeftPanel.add(avgButton);
        lowerLeftPanel.add(createButton("Employee info", e -> {

            comboBox.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, controller.getUserInfo(getIdTableValue()));

        }));
        leftPanel.add(lowerLeftPanel, BorderLayout.SOUTH);

        rightPanel.add(setFontToComponent(new JLabel("First name:")));
        rightPanel.add(firstNameTextField);
        rightPanel.add(setFontToComponent(new JLabel("Last name:")));
        rightPanel.add(lastNameTextField);
        JPanel listsPanel = new JPanel(new GridLayout(2, 2, 5, 0));
        listsPanel.add(setFontToComponent(new JLabel("Select district:")));
        listsPanel.add(setFontToComponent(new JLabel("Select position:")));
        listsPanel.add(comboBoxDistricts);
        listsPanel.add(comboBoxPositions);
        rightPanel.add(listsPanel);
        rightPanel.add(setFontToComponent(new JLabel("Salary:")));
        rightPanel.add(salaryTextField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));

        buttonPanel.add(createButton("Add employee", e -> {

            controller.saveEmployee(firstNameTextField.getText(), lastNameTextField.getText(), salaryTextField.getText(), comboBoxDistricts.getSelectedIndex(), comboBoxPositions.getSelectedIndex());
            comboBox.setSelectedIndex(0);
        }));

        buttonPanel.add(removeButton);
        JPanel secondaryButtonPanel = new JPanel(new GridLayout(2, 1, 0, 10));

        secondaryButtonPanel.add(createButton("Add district", e -> {
            String districtName = (String) JOptionPane.showInputDialog(panel, "Enter new district name", "", JOptionPane.PLAIN_MESSAGE, null, null, "");
            controller.saveDistrict(districtName);
            comboBox.setSelectedIndex(2);
            comboBoxDistricts.setModel(new DefaultComboBoxModel(controller.getDataListForComboBox(2)));

        }));
        secondaryButtonPanel.add(createButton("Add position", e -> {
            String positionName = (String) JOptionPane.showInputDialog(panel, "Enter new position name", "", JOptionPane.PLAIN_MESSAGE, null, null, "");
            controller.savePosition(positionName);
            comboBox.setSelectedIndex(1);
            comboBoxPositions.setModel(new DefaultComboBoxModel(controller.getDataListForComboBox(1)));
        }));

        buttonPanel.add(secondaryButtonPanel);
        rightPanel.add(buttonPanel);

        panel.add(leftPanel);
        panel.add(rightPanel);
        add(panel);
    }

    private JComponent setFontToComponent(JComponent component) {
        component.setFont(TEXT_FONT);
        return component;
    }


    private JButton createButton(String name, ActionListener listener) {
        JButton button = new JButton(name);

        button.setEnabled(true);
        button.addActionListener(listener);
        return button;
    }

    private int getSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            selectedRow = 0;
        }
        return selectedRow;
    }

    private int getIdTableValue() {
        int selectedRow = getSelectedRow();
        if (table.getRowCount() > 0) {
            return Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        } else {
            return -1;
        }
    }
}
