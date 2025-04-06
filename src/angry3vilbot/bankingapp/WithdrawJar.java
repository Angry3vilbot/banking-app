package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * WithdrawJar class is a GUI component that allows users to withdraw money from a jar.
 * It extends the {@link Api} class and implements the necessary components and layout for the withdrawal process.
 * <br><br>
 * It includes fields for the jar title and amount to withdraw, as well as buttons for withdrawing and canceling the action.
 * The class handles user input validation and communicates with the database to perform the withdrawal operation.
 * It also updates the UI components to reflect the changes after the withdrawal.
 */
public class WithdrawJar extends Api {
    /**
     * {@link GridBagLayout} object used for layout management.
     */
    GridBagLayout gbLayout;
    /**
     * {@link GridBagConstraints} object used for layout management.
     */
    GridBagConstraints gbc;
    /**
     * Label for the title of the withdrawal screen.
     */
    JLabel title;
    /**
     * ID of the jar to withdraw from.
     */
    int jarId;
    /**
     * Label to display the title of the jar.
     */
    JLabel titleLabel;
    /**
     * Label to display the amount available in the jar.
     */
    JLabel amountLabel;
    /**
     * Field to display the title of the jar.
     */
    JTextField titleField;
    /**
     * Field for entering the amount to withdraw.
     */
    JTextField amountField;
    /**
     * Button to withdraw money from the jar.
     */
    JButton withdrawBtn;
    /**
     * Button to cancel the withdrawal action.
     */
    JButton cancelBtn;

    /**
     * Constructs a WithdrawJar object.
     */
    WithdrawJar() {
        gbLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        this.setLayout(gbLayout);
        title = new JLabel("Withdraw from Jar");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        titleLabel = new JLabel("Title");
        amountLabel = new JLabel("Amount");
        titleField = new JTextField();
        titleField.setEditable(false);
        titleField.setColumns(15);
        amountField = new JTextField();
        amountField.setColumns(15);
        withdrawBtn = new JButton("Withdraw");
        cancelBtn = new JButton("Cancel");
        withdrawBtn.addActionListener(this::withdrawHandler);
        cancelBtn.addActionListener(this::cancelHandler);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(title, gbc);

        // Title Label
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        this.add(titleLabel, gbc);

        // Title Field
        gbc.gridx = 1;
        this.add(titleField, gbc);

        // Amount Label
        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(amountLabel, gbc);

        // Amount Field
        gbc.gridx = 1;
        this.add(amountField, gbc);

        // Cancel Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(cancelBtn, gbc);

        // Withdraw Button
        gbc.gridx = 1;
        this.add(withdrawBtn, gbc);
    }

    /**
     * Handles the action event triggered by the withdraw button.
     * Validates the user input, checks if the withdrawal amount is valid,
     * and performs the withdrawal operation.
     * If the withdrawal is successful, it updates the UI components and switches back to the Jars view.
     * @param event The action event triggered by the withdraw button.
     */
    void withdrawHandler(ActionEvent event) {
        try {
            String amount = amountField.getText();
            double amountValue;
            boolean isBroken = false;
            if (amount.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an amount to withdraw.");
                return;
            }
            if (!amount.matches("[0-9]+([.][0-9]{1,2})?") && Double.parseDouble(amountField.getText()) < 0) {
                JOptionPane.showMessageDialog(this, "The amount must be larger than or equal to 0 and of format 123.32 or 123", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                amountValue = Double.parseDouble(amount);
            }
            if (amountValue > Double.parseDouble(amountLabel.getClientProperty("available").toString())) {
                JOptionPane.showMessageDialog(this, "You cannot withdraw more than the available amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(amountValue == Double.parseDouble(amountLabel.getClientProperty("available").toString())) {
                int userChoice = JOptionPane.showConfirmDialog(this, "You are about to withdraw all money from this jar. Do you want to break the jar, deleting it?", "Withdraw all money", JOptionPane.YES_NO_OPTION);
                isBroken = userChoice != 1;
            }

            withdrawFromJar(jarId, amountValue, isBroken);
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            // Update the user information
            try {
                update();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage() + "\n" + exception.getCause());
            }
            // Revert everything to initial values
            titleField.setText("");
            amountField.setText("");
            // Update the UI of Jars and Main
            Jars jars = (Jars) getParent().getComponent(2);
            jars.updateUI();
            Main main = (Main) getParent().getComponent(4);
            main.updateUI();
            // Switch card back to Jars
            CardLayout layout = (CardLayout) getParent().getLayout();
            layout.show(getParent(), "jars");
        }
    }

    /**
     * Handles the action event triggered by the cancel button.
     * Clears the input fields and switches back to the Jars view.
     * @param event The action event triggered by the cancel button.
     */
    void cancelHandler(ActionEvent event) {
        amountField.setText("");
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "jars");
    }

    /**
     * Sets the title and ID of the jar to be withdrawn from.
     * This method is used to initialize the jar information in the UI.
     * @param title the title of the jar
     * @param id the id of the jar
     */
    public void setTitleAndID(String title, int id) {
        titleField.setText(title);
        jarId = id;
    }

    /**
     * Sets the amount label to display the available amount in the jar.
     * This method is used to update the UI with the current amount available for withdrawal.
     * @param amount the amount of money available in the jar
     */
    public void setAmountLabel(String amount) {
        amountLabel.setText("Amount (" + amount + " available)");
        amountLabel.putClientProperty("available", amount.substring(0, amount.length() - 2));
    }
}
