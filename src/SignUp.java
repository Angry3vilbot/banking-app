import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;

public class SignUp extends Api {
    JLabel nameLabel;
    JLabel passLabel;
    JLabel confirmLabel;
    JTextField nameField;
    JPasswordField passField;
    JPasswordField confirmField;
    JButton submitBtn;
    JButton loginBtn;

    SignUp() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        nameLabel = new JLabel("Name");
        passLabel = new JLabel("Password");
        confirmLabel = new JLabel("Confirm Password");
        nameField = new JTextField();
        passField = new JPasswordField();
        confirmField = new JPasswordField();
        submitBtn = new JButton("Sign Up");
        loginBtn = new JButton("Log In");

        nameField.setColumns(15);
        passField.setColumns(15);
        confirmField.setColumns(15);
        submitBtn.addActionListener(this::submitBtnHandler);
        loginBtn.addActionListener(this::loginBtnHandler);
        this.setLayout(grid);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.insets = new Insets(0, 0, 10, 0);
        this.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(confirmLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(confirmField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        this.add(submitBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        this.add(loginBtn, gbc);
    }

    private void submitBtnHandler(ActionEvent event) {
        if (passField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Error: Enter the password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (passField.getPassword().length > 72) {
            JOptionPane.showMessageDialog(null, "Error: Password must be less than 72 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!Arrays.equals(passField.getPassword(), confirmField.getPassword())) {
            JOptionPane.showMessageDialog(null, "Error: The passwords must match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: You must enter a name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(nameField.getText().trim().matches("[A-z ]+")) {
            JOptionPane.showMessageDialog(null, "Error: The name must only contain letters and spaces", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            signUp(nameField.getText(), passField.getPassword());
            nameField.setText("");
            passField.setText("");
            confirmField.setText("");
            CardLayout layout = (CardLayout) getParent().getLayout();
            layout.next(getParent());
            layout.show(getParent(), "main");
            // Update the Main component
            Main mainPanel = (Main) getParent().getComponent(4);
            mainPanel.updateUI();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loginBtnHandler(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "login");
    }
}
