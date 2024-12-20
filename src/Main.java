import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JPanel {
    GridBagConstraints gbc;
    JLabel name;
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
            name = new JLabel(currentUser.getName(), SwingConstants.LEFT);
            name.setFont(new Font("Dialog", Font.BOLD, 32));
            balance = new JLabel(currentUser.getBalance() + " €", SwingConstants.CENTER);
            balance.setFont(new Font("Dialog", Font.BOLD, 32));

            // Name
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            this.add(name, gbc);

            // Balance
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(balance, gbc);
        }
        deposit = new JButton("Deposit", new ImageIcon(getClass().getResource("assets/deposit.png")));
        send = new JButton("Send", new ImageIcon(getClass().getResource("assets/send.png")));
        Dimension btnSize = new Dimension(200, 60);
        deposit.setPreferredSize(btnSize);
        deposit.setFocusable(false);
        deposit.addActionListener(this::deposit);
        send.setPreferredSize(btnSize);
        send.setFocusable(false);
        send.addActionListener(this::send);
        navigation = new Navigation();

        // Deposit button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 0, 50);
        this.add(deposit, gbc);

        // Send button
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(send, gbc);

        // Navigation
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(navigation, gbc);
    }

    public void updateUI() {
        User currentUser = UserSession.getInstance().getUser();
        // If logged in
        if(currentUser != null) {
            if(balance != null) {
                this.remove(balance);
            }
            if(name != null) {
                this.remove(name);
            }
            name = new JLabel(currentUser.getName(), SwingConstants.LEFT);
            name.setFont(new Font("Dialog", Font.BOLD, 32));
            balance = new JLabel(currentUser.getBalance() + " €", SwingConstants.CENTER);
            balance.setFont(new Font("Dialog", Font.BOLD, 32));

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            this.add(name, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(balance, gbc);
        }
        this.revalidate();
        this.repaint();
    }

    private void deposit(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "deposit");
    }
    private void send(ActionEvent e) {

    }
}
