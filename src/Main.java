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
    Transactions transactions;
    JScrollPane transactionsScroll;
    Main() {
        User currentUser = UserSession.getInstance().getUser();
        gbLayout = new GridBagLayout();
        this.setLayout(gbLayout);
        transactions = new Transactions();
        transactionsScroll = new JScrollPane(transactions, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        transactionsScroll.setPreferredSize(new Dimension(300, 300));
        JScrollBar verticalScrollBar = transactionsScroll.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);
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

            // Transactions
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(transactionsScroll, gbc);
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
            if(transactionsScroll != null) {
                this.remove(transactionsScroll);
            }
            name = new JLabel(currentUser.getName(), SwingConstants.LEFT);
            name.setFont(new Font("Dialog", Font.BOLD, 32));
            balance = new JLabel(currentUser.getBalance() + " €", SwingConstants.CENTER);
            balance.setFont(new Font("Dialog", Font.BOLD, 32));
            transactions = new Transactions();
            transactionsScroll = new JScrollPane(transactions);
            transactionsScroll.setPreferredSize(new Dimension(300, 300));
            JScrollBar verticalScrollBar = transactionsScroll.getVerticalScrollBar();
            verticalScrollBar.setUnitIncrement(16);

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

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.weightx = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(transactionsScroll, gbc);
        }
        this.revalidate();
        this.repaint();
    }

    private void deposit(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "deposit");
    }
    private void send(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "send");
    }

    class Transactions extends JPanel {
        User currentUser;
        GridBagLayout gbLayout;
        GridBagConstraints gridBagConstraints;
        JPanel transaction;
        JLabel caption;
        JLabel title;
        JLabel value;
        JLabel sender;
        JLabel destination;
        Transactions() {
            currentUser = UserSession.getInstance().getUser();
            caption = new JLabel("Transactions");
            gbLayout = new GridBagLayout();
            gridBagConstraints = new GridBagConstraints();
            this.setLayout(gbLayout);

            caption.setFont(new Font("Dialog", Font.BOLD, 20));
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            this.add(caption, gridBagConstraints);

            if(currentUser != null) {
                for (int i = 0; i < currentUser.getTransactions().length; i++) {
                    transaction = new JPanel();
                    GridBagLayout tgbl = new GridBagLayout();
                    GridBagConstraints tgbc = new GridBagConstraints();
                    transaction.setLayout(tgbl);

                    title = new JLabel(currentUser.getTransactions()[i].getTitle());
                    tgbc.gridx = 0;
                    tgbc.gridy = 0;
                    transaction.add(title, tgbc);

                    if(currentUser.getTransactions()[i].getSender() != null) {
                        sender = new JLabel("From " + currentUser.getTransactions()[i].getSender());
                        tgbc.gridy = 3;
                        transaction.add(sender, tgbc);

                        value = new JLabel(currentUser.getTransactions()[i].getValue() + " €");
                        value.setForeground(Color.GREEN);
                        tgbc.gridx = 1;
                        tgbc.gridy = 2;
                        transaction.add(value, tgbc);
                    }
                    else {
                        destination = new JLabel("To " + currentUser.getTransactions()[i].getDestination());
                        tgbc.gridy = 3;
                        transaction.add(destination, tgbc);

                        value = new JLabel(currentUser.getTransactions()[i].getValue() + " €");
                        tgbc.gridx = 1;
                        tgbc.gridy = 2;
                        transaction.add(value, tgbc);
                    }
                    transaction.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
                    gridBagConstraints.gridy = i+1;
                    gridBagConstraints.insets = new Insets(50, 0, 0, 0);
                    this.add(transaction, gridBagConstraints);
                }
            }
        }
    }
}
