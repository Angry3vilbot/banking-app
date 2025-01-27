import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Send extends Api {
    JLabel title;
    ButtonGroup newRecentGroup;
    JPanel newRecentContainer;
    JButton newBtn;
    JButton recentBtn;
    JLabel nameL;
    JLabel numberL;
    JLabel amountL;
    JTextField name;
    JTextField number;
    JTextField amount;
    JButton submitBtn;
    JButton cancelBtn;

    Send() {
        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        title = new JLabel("Send");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        newRecentGroup = new ButtonGroup();
        newRecentContainer = new JPanel();
        newBtn = new JButton("New");
        recentBtn = new JButton("Recent");
        nameL = new JLabel("Name");
        numberL = new JLabel("Card Number");
        amountL = new JLabel("Amount");
        name = new JTextField();
        number = new JTextField();
        amount = new JTextField();
        submitBtn = new JButton("Send");
        cancelBtn = new JButton("Cancel");

        newRecentGroup.add(newBtn);
        newRecentGroup.add(recentBtn);
        newRecentContainer.add(newBtn);
        newRecentContainer.add(recentBtn);
        name.setColumns(15);
        number.setColumns(15);
        amount.setColumns(15);
        submitBtn.addActionListener(this::submitBtnHandler);
        cancelBtn.addActionListener(this::cancelBtnHandler);
        this.setLayout(gbLayout);

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

        // Name label
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        this.add(nameL, gbc);

        // Name field
        gbc.gridx = 1;
        this.add(name, gbc);

        // Card Number label
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(numberL, gbc);

        // Card Number Field
        gbc.gridx = 1;
        this.add(number, gbc);

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

    private void submitBtnHandler(ActionEvent e) {
        try {
            BigDecimal paymentInfoData = new BigDecimal(1);
            double amountData;
            String nameData = "";

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

            // Validate amountData
            if(amount.getText().matches("[0-9]+[.]?[0-9]*") && !amount.getText().equals("0")) {
                amountData = Double.parseDouble(amount.getText());
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: The amount must be larger than 0 and of format 123.321 or 123", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate nameData
            if(name.getText().matches("[A-z ]+")) {
                nameData = name.getText();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error: Name must only consist of alphabetical characters", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            send(amountData, paymentInfoData, nameData);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage() + "\n" + exception.getCause());
            for (StackTraceElement i : exception.getStackTrace()) {
                System.out.println(i);
            }
        } finally {
            // Update the user information
            try {
                update();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage() + "\n" + exception.getCause());
            }
            // Revert everything to initial values
            newRecentGroup.clearSelection();
            newRecentGroup.setSelected(newBtn.getModel(), true);
            name.setText("");
            number.setText("");
            amount.setText("");
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
        newRecentGroup.clearSelection();
        newRecentGroup.setSelected(newBtn.getModel(), true);
        name.setText("");
        number.setText("");
        amount.setText("");
        // Switch card back to Main
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "main");
    }
}
