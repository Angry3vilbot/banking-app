import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class Main extends Api {
    GridBagConstraints gbc;
    JLabel name;
    JLabel cardnumber;
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
            cardnumber = new JLabel("Card Number: ****************" , SwingConstants.RIGHT);
            cardnumber.setFont(new Font("Dialog", Font.BOLD, 32));
            cardnumber.setPreferredSize(new Dimension(400, cardnumber.getPreferredSize().height));
            cardnumber.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cardnumber.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Show the card number if it is hidden
                    if(cardnumber.getText().equals("Card Number: ****************")) {
                        cardnumber.setText("Card Number: " + currentUser.getCardnumber());
                    }
                    else {
                        cardnumber.setText("Card Number: ****************");
                    }
                    // Update the UI
                    SwingUtilities.invokeLater(() -> {
                        Window window = SwingUtilities.getWindowAncestor(Main.this);
                        if(window != null) {
                            window.revalidate();
                            window.repaint();
                        }
                    });
                }
            });
            balance = new JLabel(currentUser.getBalance() + " €", SwingConstants.CENTER);
            balance.setFont(new Font("Dialog", Font.BOLD, 32));
            //Display a dialog box for each Deposit Request the user has
            for (int i = 0; i < currentUser.getRequests().length; i++) {
                DepositRequest request = currentUser.getRequests()[i];
                String message = request.getName() + " (" + request.getTarget() + ") is requesting " + request.getAmount() + " € from your account.";
                int result = JOptionPane.showConfirmDialog(this, message, "Deposit Request", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(result == JOptionPane.YES_OPTION) {
                    try{
                        fulfillDepositRequest(request.getId());
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(this, "Error fulfilling request: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    try{
                        deleteDepositRequest(request.getId());
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            // Name
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            this.add(name, gbc);

            // Card Number
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.FIRST_LINE_END;
            this.add(cardnumber, gbc);

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
        gbc.insets = new Insets(0, 0, 0, 10);
        this.add(deposit, gbc);

        // Send button
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 10, 0, 0);
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
            cardnumber = new JLabel("Card Number: ****************" , SwingConstants.RIGHT);
            cardnumber.setFont(new Font("Dialog", Font.BOLD, 32));
            cardnumber.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cardnumber.setPreferredSize(new Dimension(400, cardnumber.getPreferredSize().height));
            cardnumber.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Show the card number if it is hidden
                    if(cardnumber.getText().equals("Card Number: ****************")) {
                        cardnumber.setText("Card Number: " + currentUser.getCardnumber());
                    }
                    else {
                        cardnumber.setText("Card Number: ****************");
                    }
                    // Update the UI
                    SwingUtilities.invokeLater(() -> {
                        Window window = SwingUtilities.getWindowAncestor(Main.this);
                        if(window != null) {
                            window.revalidate();
                            window.repaint();
                        }
                    });
                }
            });
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
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.FIRST_LINE_END;
            this.add(cardnumber, gbc);

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
            //Display a dialog box for each Deposit Request the user has
            for (int i = 0; i < currentUser.getRequests().length; i++) {
                DepositRequest request = currentUser.getRequests()[i];
                String message = request.getName() + " (" + request.getTarget() + ") is requesting " + request.getAmount() + " € from your account.";
                int result = JOptionPane.showConfirmDialog(this, message, "Deposit Request", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(result == JOptionPane.YES_OPTION) {
                    try{
                        fulfillDepositRequest(request.getId());
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(this, "Error fulfilling request: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    try{
                        deleteDepositRequest(request.getId());
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            // Check if the user has jars that have reached their goal and hasn't already acknowledged the message
            if(UserSession.getInstance().getUser().getJars().length != 0 && !UserSession.getInstance().getUser().getAcknowledgedJarGoalMsg()) {
                int count = 0;
                StringBuilder jarString = new StringBuilder();
                for (Jar jar : UserSession.getInstance().getUser().getJars()) {
                    if (jar.getGoal() != 0.0 && jar.getBalance() >= jar.getGoal()) {
                        count++;
                        jarString.append("\"");
                        jarString.append(jar.getTitle());
                        jarString.append("\"");
                        if (count >= 1) {
                            jarString.append(", ");
                        }
                    }
                }
                // Delete the space and comma after the last jar
                jarString.delete(jarString.length() - 2, jarString.length());
                if(count == 1) {
                    JOptionPane.showMessageDialog(this, "Jar " + jarString + " has reached its goal", "Jar Goal Reached", JOptionPane.PLAIN_MESSAGE);
                }
                else if (count >= 2) {
                    JOptionPane.showMessageDialog(this, "Jars " + jarString + " have reached their goals", "Jar Goal Reached", JOptionPane.PLAIN_MESSAGE);
                }

                UserSession.getInstance().getUser().setAcknowledgedJarGoalMsg(true);
                // Refresh the UI
                SwingUtilities.invokeLater(() -> {
                    Window window = SwingUtilities.getWindowAncestor(this);
                    if(window != null) {
                        window.revalidate();
                        window.repaint();
                    }
                });
            }
        }
        // Refresh the UI
        SwingUtilities.invokeLater(() -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if(window != null) {
                window.revalidate();
                window.repaint();
            }
        });
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
