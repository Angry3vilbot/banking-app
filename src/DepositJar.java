import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class DepositJar extends Api {
    GridBagLayout gbLayout;
    GridBagConstraints gbc;
    JLabel title;
    JLabel idLabel;
    JLabel amountLabel;
    JTextField idField;
    JTextField amountField;
    JButton depositBtn;
    JButton cancelBtn;

    DepositJar() {
        gbLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        this.setLayout(gbLayout);
        title = new JLabel("Deposit to Jar");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        idLabel = new JLabel("ID");
        amountLabel = new JLabel("Amount");
        idField = new JTextField();
        amountField = new JTextField();
        idField.setColumns(15);
        amountField.setColumns(15);
        depositBtn = new JButton("Deposit");
        cancelBtn = new JButton("Cancel");
        depositBtn.addActionListener(this::depositHandler);
        cancelBtn.addActionListener(this::cancelHandler);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(title, gbc);

        // ID Label
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        this.add(idLabel, gbc);

        // ID Field
        gbc.gridx = 1;
        this.add(idField, gbc);

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

        // Deposit Button
        gbc.gridx = 1;
        this.add(depositBtn, gbc);
    }

    private void depositHandler(ActionEvent e) {
        try {
            int id;
            double amount;
            if(idField.getText().matches("[0-9]+")) {
                id = Integer.parseInt(idField.getText());
            }
            else {
                JOptionPane.showMessageDialog(this, "ID must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(amountField.getText().matches("[0-9]+([.][0-9]{1,2})?") && Double.parseDouble(amountField.getText()) > 0) {
                amount = Double.parseDouble(amountField.getText());
            }
            else {
                JOptionPane.showMessageDialog(this, "The amount must be larger than 0 and of format 123.32 or 123", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (amount > 1000) {
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to deposit more than 1000€?", "Warning", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            depositToJar(id, amount);
        }
        catch (SQLException exception) {
            JOptionPane.showMessageDialog(this, "Error: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            // Update the user information
            try {
                update();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage() + "\n" + exception.getCause());
            }
            // Revert everything to initial values
            idField.setText("");
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
    private void cancelHandler(ActionEvent e) {
        idField.setText("");
        amountField.setText("");
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "jars");
    }
}
