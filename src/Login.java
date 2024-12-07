import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends Api{
    JLabel loginLabel;
    JLabel passLabel;
    JPasswordField passField;
    JTextField loginField;
    JButton submitBtn;

    Login() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        loginLabel = new JLabel("Card Number");
        passLabel = new JLabel("Password");
        passField = new JPasswordField();
        loginField = new JTextField();
        submitBtn = new JButton("Log In");

        loginField.setColumns(15);
        passField.setColumns(15);
        submitBtn.addActionListener(this::loginBtnHandler);
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

    private void loginBtnHandler(ActionEvent e) {
        try {
            String result = authenticate(loginField.getText(), passField.getPassword());
            JOptionPane.showMessageDialog(null, result);
            System.out.println(result);
        } catch (SQLException exception) {
            // Handle the exception
        }
    }
}
