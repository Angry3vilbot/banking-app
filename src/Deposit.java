import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Deposit extends Api {
    JRadioButton freeMoney;
    JRadioButton fromCard;
    JPanel radioContainer;
    ButtonGroup paymentMethod;
    JTextField paymentInfo;
    JTextField depositAmount;
    JLabel paymentInfoLabel;
    JLabel depositAmountLabel;
    JButton submitBtn;
    JButton cancelBtn;

    Deposit() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
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

        // Radio button container
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(radioContainer, gbc);

        // Payment Info Label
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        this.add(paymentInfoLabel, gbc);

        // Payment Info Field
        gbc.gridx = 1;
        this.add(paymentInfo, gbc);

        // Deposit Amount Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(depositAmountLabel, gbc);

        // Deposit Amount Field
        gbc.gridx = 1;
        this.add(depositAmount, gbc);

        // Cancel Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(cancelBtn, gbc);

        // Submit Button
        gbc.gridx = 1;
        this.add(submitBtn, gbc);
    }

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
            if(depositAmount.getText().matches("[0-9]+[.]?[0-9]*") && !depositAmount.getText().equals("0")) {
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
            Main main = (Main) getParent().getComponent(3);
            main.updateUI();
            // Switch card back to Main
            CardLayout layout = (CardLayout) getParent().getLayout();
            layout.show(getParent(), "main");
        }
    }

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
