package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * Login class is a {@link JPanel} that contains the login form for the application.
 * It contains fields for the user to enter their card number and password,
 * as well as buttons to submit the form or navigate to the sign-up screen.
 * <br><br>
 * The login button triggers the authentication process,
 * and if successful, it navigates to the main screen of the application.
 * The sign-up button navigates to the sign-up screen.
 */
public class Login extends Api{
    /**
     * Label for the card number field.
     */
    JLabel loginLabel;
    /**
     * Label for the password field.
     */
    JLabel passLabel;
    /**
     * Field for the user to enter their password.
     */
    JPasswordField passField;
    /**
     * Field for the user to enter their card number.
     */
    JTextField loginField;
    /**
     * Button to submit the login form.
     */
    JButton submitBtn;
    /**
     * Button to navigate to the sign-up screen.
     */
    JButton signUpBtn;

    /**
     * Constructs a Login object.
     */
    Login() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        loginLabel = new JLabel("Card Number");
        passLabel = new JLabel("Password");
        passField = new JPasswordField();
        loginField = new JTextField();
        submitBtn = new JButton("Log In");
        signUpBtn = new JButton("Sign Up");

        loginField.setColumns(15);
        passField.setColumns(15);
        submitBtn.addActionListener(this::loginBtnHandler);
        signUpBtn.addActionListener(this::signUpBtnHandler);
        this.setLayout(grid);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.insets = new Insets(0, 0, 10, 0);
        this.add(loginLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(loginField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        this.add(submitBtn, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        this.add(signUpBtn, gbc);
    }

    /**
     * Handles the action of the login button.
     * It retrieves the card number and password from the input fields,
     * authenticates the user, and navigates to the main screen if successful.
     * If authentication fails, it shows an error message.
     * @param e the event that triggered the action
     */
    private void loginBtnHandler(ActionEvent e) {
        try {
            authenticate(loginField.getText(), passField.getPassword());
            passField.setText("");
            CardLayout layout = (CardLayout) getParent().getLayout();
            layout.next(getParent());
            layout.show(getParent(), "main");
            // Update the Main component
            Main mainPanel = (Main) getParent().getComponent(4);
            mainPanel.updateUI();
            // Update the Jars component
            Jars jars = (Jars) getParent().getComponent(2);
            jars.updateUI();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the action of the sign-up button.
     * It navigates to the sign-up screen.
     * @param e the event that triggered the action
     */
    private void signUpBtnHandler(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "signup");
    }
}
