package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * SignUp class is a {@link JPanel} that allows the user to sign up for an account.
 * It contains a form with fields for the user's name, password, and password confirmation.
 * It also contains buttons to submit the form and to switch to the login screen.
 */
public class SignUp extends Api {
    /**
     * Label for {@link #nameField}.
     */
    JLabel nameLabel;
    /**
     * Label for {@link #passField}.
     */
    JLabel passLabel;
    /**
     * Label for {@link #confirmField}.
     */
    JLabel confirmLabel;
    /**
     * Field to enter the name.
     */
    JTextField nameField;
    /**
     * Field to enter the password.
     */
    JPasswordField passField;
    /**
     * Field to confirm the password.
     */
    JPasswordField confirmField;
    /**
     * Button to submit the form.
     */
    JButton submitBtn;
    /**
     * Button to switch to the login screen.
     */
    JButton loginBtn;

    /**
     * Constructs a SignUp object.
     */
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

    /**
     * Handles the action of the submit button.
     * It checks <ul>
     * <li>if the password and confirmation password match</li>
     * <li>if the name is not empty</li>
     * <li>if the password is less than 72 characters long.</li></ul>
     * If all checks pass, it calls the {@link Api#signUp} method to create a new account.
     * If any check fails, it shows an error message.
     * @param event the event that triggered the method
     */
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

    /**
     * Handles the action of the login button.
     * It switches the view to the login screen.
     * @param e the event that triggered the method
     */
    private void loginBtnHandler(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "login");
    }
}
