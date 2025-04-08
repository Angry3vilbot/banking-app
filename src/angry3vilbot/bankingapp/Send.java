package angry3vilbot.bankingapp;

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

/**
 * Send class is a {@link JPanel} that allows the user to send money to another person's account.
 * It contains a title, radio buttons for selecting between new and recent entries,
 * text fields for entering the recipient's name, card number, and amount to send,
 * and buttons for submitting or canceling the transaction.
 * <br><br>
 * If the user clicks the send button, it will call the {@link Api#send(double, BigDecimal, String)} method
 * with the entered data.
 * If the user clicks the cancel button, it will revert the fields to their initial values
 * and switch the card back to the {@link Main} panel.
 */
public class Send extends Api {
    /**
     * Label for the title of the panel.
     */
    JLabel title;
    /**
     * {@link ButtonGroup} for the new/recent buttons.
     */
    ButtonGroup newRecentGroup;
    /**
     * Container for the new/recent buttons.
     */
    JPanel newRecentContainer;
    /**
     * Container that wraps the text fields and combo boxes containers to allow switching between the two.
     */
    JPanel inputsContainer;
    /**
     * Container for the text fields.
     */
    JPanel textFieldsContainer;
    /**
     * Container for the combo boxes.
     */
    JPanel comboBoxContainer;
    /**
     * Button to switch to entering new payment info.
     */
    JButton newBtn;
    /**
     * Button to switch to using recent entries.
     */
    JButton recentBtn;
    /**
     * Label for the name field.
     */
    JLabel nameL;
    /**
     * Label for the card number field.
     */
    JLabel numberL;
    /**
     * Label for the amount field.
     */
    JLabel amountL;
    /**
     * Label for the name combo box.
     */
    JLabel nameLCombo;
    /**
     * Label for the card number combo box.
     */
    JLabel numberLCombo;
    /**
     * Field for the name.
     */
    JTextField name;
    /**
     * Field for the card number.
     */
    JTextField number;
    /**
     * Field for the amount to be sent.
     */
    JTextField amount;
    /**
     * ComboBox for the name.
     */
    JComboBox<String> nameBox;
    /**
     * ComboBox for the card number.
     */
    JComboBox<String> numberBox;
    /**
     * Button to submit the transaction.
     */
    JButton submitBtn;
    /**
     * Button to cancel the transaction.
     */
    JButton cancelBtn;
    /**
     * List of recent entries read from the file.
     */
    List<String> recentEntriesList;

    /**
     * Constructs a Send object.
     */
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

    /**
     * Handles the selection of the "New" or "Recent" buttons.
     * It switches the input fields between text fields and combo boxes.
     * @param e the event that triggered the method
     */
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

    /**
     * Handles the selection logic of the combo boxes.
     * It sets the selected index of the other {@link JComboBox} to be the same as the one that was used to make the selection.
     * @param e the event that triggered the method
     */
    private void comboBoxHandler(ActionEvent e) {
        // Sets the index of the other box to be the same as the box that was used to make the selection
        if(e.getSource().equals(nameBox)) {
            numberBox.setSelectedIndex(nameBox.getSelectedIndex());
        }
        else {
            nameBox.setSelectedIndex(numberBox.getSelectedIndex());
        }
    }

    /**
     * Reads the recent entries from a file and stores them in a list.
     * If the file does not exist, it initializes an empty list.
     * This method is called when the class is instantiated.
     * <br><br>
     * It reads the entries from the file called "recentTransfers.txt".
     * If the file does not exist, it initializes an empty list.
     */
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

    /**
     * Saves the payment information to a file.
     * This method is called when the user clicks the send button.
     * It writes the entries to a file called "recentTransfers.txt".
     * If the file does not exist, it creates a new one.
     * <br><br>
     * It takes a {@link List} of entries as input and writes each entry to the file.
     * Each entry is written on a new line.
     * @param entries the list of entries to be saved
     */
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

    /**
     * Handles the submit button click event.
     * <br><br>
     * Validates the input fields and performs the send operation.
     * If the input is valid, it calls the {@link Api#send(double, BigDecimal, String)} method
     * with the entered data.
     * @param e the event that triggered the method
     */
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

    /**
     * Handles the cancel button click event.
     * Reverts the input fields to their initial values
     * and switches the card back to the {@link Main} panel.
     * @param e the event that triggered the method
     */
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
