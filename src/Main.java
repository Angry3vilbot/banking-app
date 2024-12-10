import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    GridBagConstraints gbc;
    JLabel balance;
    Navigation navigation;
    GridBagLayout gbLayout;
    JButton deposit;
    JButton send;
    Main() {
        User currentUser = UserSession.getInstance().getUser();
        gbLayout = new GridBagLayout();
        this.setLayout(gbLayout);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if(currentUser != null) {
            balance = new JLabel(currentUser.getBalance() + " €", SwingConstants.CENTER);
            balance.setFont(new Font("Dialog", Font.BOLD, 32));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(balance, gbc);
        }
        deposit = new JButton("Deposit", new ImageIcon(getClass().getResource("assets/deposit.png")));
        send = new JButton("Send", new ImageIcon(getClass().getResource("assets/send.png")));
        Dimension btnSize = new Dimension(300, 60);
        deposit.setPreferredSize(btnSize);
        deposit.setFocusable(false);
        send.setPreferredSize(btnSize);
        send.setFocusable(false);
        navigation = new Navigation();

        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(deposit, gbc);

        gbc.gridx = 1;
        this.add(send, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        this.add(navigation, gbc);
    }

    public void updateUI() {
        User currentUser = UserSession.getInstance().getUser();
        // If logged in
        if(currentUser != null) {
            if(balance != null) {
                this.remove(balance);
            }
            balance = new JLabel(currentUser.getBalance() + " €", SwingConstants.CENTER);
            balance.setFont(new Font("Dialog", Font.BOLD, 32));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(balance, gbc);
        }
        this.revalidate();
        this.repaint();
    }
}
