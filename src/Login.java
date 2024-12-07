import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {
    JLabel loginLabel;
    JLabel passLabel;
    JPasswordField passField;
    JTextField loginField;
    JButton submitBtn;

    Login() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        loginLabel = new JLabel("Name/Card Number");
        passLabel = new JLabel("Password");
        passField = new JPasswordField();
        loginField = new JTextField();
        submitBtn = new JButton("Log In");

        loginField.setColumns(15);
        passField.setColumns(15);
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
    }
}
