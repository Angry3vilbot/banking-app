import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Send extends Api {
    JLabel title;
    ButtonGroup newRecentGroup;
    JPanel newRecentContainer;
    JPanel inputsContainer;
    JPanel textFieldsContainer;
    JPanel comboBoxContainer;
    JButton newBtn;
    JButton recentBtn;
    JLabel nameL;
    JLabel numberL;
    JLabel amountL;
    JLabel nameLCombo;
    JLabel numberLCombo;
    JTextField name;
    JTextField number;
    JTextField amount;
    JComboBox<String> nameBox;
    JComboBox<String> numberBox;
    JButton submitBtn;
    JButton cancelBtn;
    List<String> recentEntriesList;

    Send() {
        readRecentEntries();
        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        title = new JLabel("Send");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        newRecentGroup = new ButtonGroup();
        newRecentContainer = new JPanel();
        inputsContainer = new JPanel();
        textFieldsContainer = new JPanel();
        comboBoxContainer = new JPanel();
        newBtn = new JButton("New");
        recentBtn = new JButton("Recent");
        nameL = new JLabel("Name");
        numberL = new JLabel("Card Number");
        amountL = new JLabel("Amount");
        nameLCombo = new JLabel("Name");
        numberLCombo = new JLabel("Card Number");
        name = new JTextField();
        number = new JTextField();
        amount = new JTextField();
        nameBox = new JComboBox<>();
        numberBox = new JComboBox<>();
        submitBtn = new JButton("Send");
        cancelBtn = new JButton("Cancel");

        newRecentGroup.add(newBtn);
        newRecentGroup.add(recentBtn);
        newRecentGroup.setSelected(newBtn.getModel(), true);
        newRecentContainer.add(newBtn);
        newRecentContainer.add(recentBtn);
        newBtn.setSelected(true);
        name.setColumns(15);
        nameBox.setPreferredSize(name.getPreferredSize());
        number.setColumns(15);
        numberBox.setPreferredSize(number.getPreferredSize());
        amount.setColumns(15);
        nameBox.setSelectedIndex(-1);
        numberBox.setSelectedIndex(-1);
        newBtn.addActionListener(this::selectionHandler);
        recentBtn.addActionListener(this::selectionHandler);
        submitBtn.addActionListener(this::submitBtnHandler);
        cancelBtn.addActionListener(this::cancelBtnHandler);
        this.setLayout(gbLayout);

        // Text Fields and Combo Box containers
        textFieldsContainer.setLayout(new GridBagLayout());
        comboBoxContainer.setLayout(new GridBagLayout());

        // Combo Boxes
        for (String s : recentEntriesList) {
            String[] entry = s.split("=", 2);
            numberBox.addItem(entry[0]);
            nameBox.addItem(entry[1]);
        }
        nameBox.addActionListener(this::comboBoxHandler);
        numberBox.addActionListener(this::comboBoxHandler);

        // Title label
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(title, gbc);

        // New/Recent button container
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(newRecentContainer, gbc);

        // Inputs container
        inputsContainer.setLayout(new CardLayout());
        inputsContainer.add("text", textFieldsContainer);
        inputsContainer.add("combo", comboBoxContainer);
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(inputsContainer, gbc);

        // Name label
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 10);
        textFieldsContainer.add(nameL, gbc);
        comboBoxContainer.add(nameLCombo, gbc);

        // Name field/combo box
        gbc.gridx = 1;
        textFieldsContainer.add(name, gbc);
        comboBoxContainer.add(nameBox, gbc);

        // Card Number label
        gbc.gridx = 0;
        gbc.gridy = 1;
        textFieldsContainer.add(numberL, gbc);
        comboBoxContainer.add(numberLCombo, gbc);

        // Card Number Field/Combo Box
        gbc.gridx = 1;
        textFieldsContainer.add(number, gbc);
        comboBoxContainer.add(numberBox, gbc);

        // Amount label
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(amountL, gbc);

        // Amount Field
        gbc.gridx = 1;
        this.add(amount, gbc);

        // Cancel Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(cancelBtn, gbc);

        // Submit Button
        gbc.gridx = 1;
        this.add(submitBtn, gbc);
    }

    private void selectionHandler(ActionEvent e) {
        if(e.getSource().equals(newBtn)) {
            newRecentGroup.setSelected(newBtn.getModel(), true);
            name.setText("");
            number.setText("");
            amount.setText("");
            CardLayout inputsLayout = (CardLayout) inputsContainer.getLayout();
            inputsLayout.show(inputsContainer, "text");
        }
        else {
            newRecentGroup.setSelected(recentBtn.getModel(), true);
            CardLayout inputsLayout = (CardLayout) inputsContainer.getLayout();
            inputsLayout.show(inputsContainer, "combo");
        }
    }

    private void comboBoxHandler(ActionEvent e) {
        // Sets the index of the other box to be the same as the box that was used to make the selection
        if(e.getSource().equals(nameBox)) {
            numberBox.setSelectedIndex(nameBox.getSelectedIndex());
        }
        else {
            nameBox.setSelectedIndex(numberBox.getSelectedIndex());
        }
    }

    private void readRecentEntries() {
        List<String> entries = new ArrayList<>();
        File file = new File("recentTransfers.txt");
        if(file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    entries.add(line);
                }
                recentEntriesList = entries;
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        else {
            recentEntriesList = new ArrayList<>();
        }
    }

    private void savePaymentInfo(List<String> entries) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("recentTransfers.txt"))) {
            for (String pair : entries) {
                writer.write(pair);
                writer.newLine();
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void submitBtnHandler(ActionEvent e) {
        try {
            BigDecimal paymentInfoData;
            double amountData;
            String nameData;
            // Checks the text input fields only if the "New" button is selected
            if (newRecentGroup.getSelection().equals(newBtn.getModel())) {
                // Validate paymentInfoData
                if (number.getText().matches("[0-9]{16}")) {
                    if (!new BigDecimal(number.getText()).equals(UserSession.getInstance().getUser().getCardnumber())) {
                        paymentInfoData = new BigDecimal(number.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: You can't send money to yourself", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Payment Info must be consist of 16 numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate nameData
                if(name.getText().matches("[A-z ]+")) {
                    nameData = name.getText().trim();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error: Name must only consist of alphabetical characters", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // Otherwise checks the combo boxes
            else {
                // Check if the combo boxes are empty
                if(numberBox.getSelectedIndex() != -1) {
                    paymentInfoData = new BigDecimal(numberBox.getSelectedItem().toString());
                    nameData = nameBox.getSelectedItem().toString();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error: You must select a card number/name to send the money to", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Validate amountData
            if(amount.getText().matches("[0-9]+([.][0-9]{1,2})?") && Double.parseDouble(amount.getText()) > 0) {
                amountData = Double.parseDouble(amount.getText());
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: The amount must be larger than 0 and of format 123.321 or 123", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            send(amountData, paymentInfoData, nameData);
            // Save the payment info and name to the cache if the operation is successful
            // Check if the info and name are already saved
            if (!recentEntriesList.contains(paymentInfoData.toString() + "=" + nameData)) {
                recentEntriesList.addFirst(paymentInfoData.toString() + "=" + nameData);
            }
            // Remove the oldest entry if the length exceeds the maximum
            if(recentEntriesList.size() > 10) {
                recentEntriesList.removeLast();
            }
            // Save the entries to the file
            savePaymentInfo(recentEntriesList);
            // Read the file again to update the cache with the new entry
            readRecentEntries();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Update the user information
            try {
                update();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Revert everything to initial values
            newRecentGroup.setSelected(newBtn.getModel(), true);
            name.setText("");
            number.setText("");
            amount.setText("");
            CardLayout inputsLayout = (CardLayout) inputsContainer.getLayout();
            inputsLayout.show(inputsContainer, "text");
            // Update the UI of Main
            Main main = (Main) getParent().getComponent(4);
            main.updateUI();
            // Switch card back to Main
            CardLayout layout = (CardLayout) getParent().getLayout();
            layout.show(getParent(), "main");
        }
    }

    private void cancelBtnHandler(ActionEvent e) {
        // Revert everything to initial values
        newRecentGroup.setSelected(newBtn.getModel(), true);
        name.setText("");
        number.setText("");
        amount.setText("");
        CardLayout inputsLayout = (CardLayout) inputsContainer.getLayout();
        inputsLayout.show(inputsContainer, "text");
        // Switch card back to Main
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "main");
    }
}
