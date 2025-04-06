package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Deposit class is a {@link JPanel} that allows the user to deposit money into their account.
 * It contains a title, radio buttons for payment method selection (Free Money or Card Number),
 * text fields for payment info and deposit amount, and buttons for submitting or canceling the deposit.
 * It validates the input and performs the deposit operation.
 * If the user clicks the deposit button, it will call the {@link Api#deposit(Double)} or {@link Api#deposit(BigDecimal, Double)} method
 * depending on the selected payment method.
 * If the user clicks the cancel button, it will revert the fields to their initial values
 * and switch the card back to the {@link Main} panel.
 */
public class Deposit extends Api {
    /**
     * Label for the title of the deposit panel.
     */
    JLabel title;
    /**
     * Radio button for selecting the payment method as "Free Money".
     */
    JRadioButton freeMoney;
    /**
     * Radio button for selecting the payment method as "Card Number".
     */
    JRadioButton fromCard;
    /**
     * Container for the radio buttons.
     */
    JPanel radioContainer;
    /**
     * Group of radio buttons for selecting the payment method.
     */
    ButtonGroup paymentMethod;
    /**
     * Text field for the payment info.
     */
    JTextField paymentInfo;
    /**
     * Text field for the deposit amount.
     */
    JTextField depositAmount;
    /**
     * Label for the {@link #paymentInfo} field
     */
    JLabel paymentInfoLabel;
    /**
     * Label for the {@link #depositAmount} field
     */
    JLabel depositAmountLabel;
    /**
     * Button to submit the deposit request
     */
    JButton submitBtn;
    /**
     * Button to revert the fields to their initial values
     */
    JButton cancelBtn;

    /**
     * Constructs a new Deposit panel.
     */
    Deposit() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        title = new JLabel("Deposit");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        radioContainer = new JPanel();
        freeMoney = new JRadioButton("Free Money", true);
        fromCard = new JRadioButton("Card Number");
        paymentMethod = new ButtonGroup();
        paymentInfo = new JTextField();
        depositAmount = new JTextField();
        paymentInfoLabel = new JLabel("Payment Info");
        depositAmountLabel = new JLabel("Amount");
        submitBtn = new JButton("Deposit");
        cancelBtn = new JButton("Cancel");

        radioContainer.add(freeMoney);
        radioContainer.add(fromCard);
        paymentMethod.add(freeMoney);
        paymentMethod.add(fromCard);
        freeMoney.setSelected(true);
        paymentInfo.setColumns(15);
        depositAmount.setColumns(15);
        submitBtn.addActionListener(this::submitBtnHandler);
        cancelBtn.addActionListener(this::cancelBtnHandler);
        this.setLayout(grid);

        // Title label
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(title, gbc);

        // Radio button container
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(radioContainer, gbc);

        // Payment Info Label
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        this.add(paymentInfoLabel, gbc);

        // Payment Info Field
        gbc.gridx = 1;
        this.add(paymentInfo, gbc);

        // Deposit Amount Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(depositAmountLabel, gbc);

        // Deposit Amount Field
        gbc.gridx = 1;
        this.add(depositAmount, gbc);

        // Cancel Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(cancelBtn, gbc);

        // Submit Button
        gbc.gridx = 1;
        this.add(submitBtn, gbc);
    }

    /**
     * Handles the submit button click event.
     * Validates the input fields and performs the deposit operation.
     * If the input is valid, it calls the appropriate deposit method
     * and updates the user information.
     * If an error occurs, it shows an error message dialog.
     * Finally, it reverts the fields to their initial values
     * and switches the card back to the {@link Main} panel.
     * @param e the event that triggered the method
     */
    private void submitBtnHandler(ActionEvent e) {
        try {
            BigDecimal paymentInfoData = new BigDecimal(1);
            Double depositAmountData;

            // Validate paymentInfo if card number is selected
            if (paymentMethod.getSelection() == fromCard.getModel()) {
                if(paymentInfo.getText().matches("[0-9]{16}")) {
                    if(!new BigDecimal(paymentInfo.getText()).equals(UserSession.getInstance().getUser().getCardnumber())) {
                        paymentInfoData = new BigDecimal(paymentInfo.getText());
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Error: You can't ask yourself for money", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error: Payment Info must be consist of 16 numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            //Validate depositAmount
            if(depositAmount.getText().matches("[0-9]+([.][0-9]{1,2})?") && Double.parseDouble(depositAmount.getText()) > 0) {
                depositAmountData = Double.parseDouble(depositAmount.getText());
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: Deposit amount must be larger than 0 and of format 123.321 or 123", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Free Money is selected
            if(paymentMethod.getSelection() == freeMoney.getModel()) {
                deposit(depositAmountData);
            }
            // Card Number is selected
            else {
                deposit(paymentInfoData, depositAmountData);
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage() + "\n" + exception.getCause());
        } finally {
            // Update the user information
            try {
                update();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage() + "\n" + exception.getCause());
            }
            // Revert everything to initial values
            paymentMethod.clearSelection();
            paymentMethod.setSelected(freeMoney.getModel(), true);
            paymentInfo.setText("");
            depositAmount.setText("");
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
     * Reverts the fields to their initial values
     * and switches the card back to the {@link Main} panel.
     * @param e the event that triggered the method
     */
    private void cancelBtnHandler(ActionEvent e) {
        // Revert everything to initial values
        paymentMethod.setSelected(freeMoney.getModel(), true);
        paymentInfo.setText("");
        depositAmount.setText("");
        // Switch card back to Main
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "main");
    }
}
