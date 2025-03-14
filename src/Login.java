import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class Login extends Api{
    JLabel loginLabel;
    JLabel passLabel;
    JPasswordField passField;
    JTextField loginField;
    JButton submitBtn;
    JButton signUpBtn;

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

    private void signUpBtnHandler(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "signup");
    }
}
