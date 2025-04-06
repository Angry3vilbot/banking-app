package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * Main class represents the Main screen of the application.
 * It displays the user's name, balance, and a list of their transactions.
 * It also provides buttons for depositing and sending money.
 */
public class Main extends Api {
    /**
     * {@link GridBagConstraints} object for layout management.
     */
    GridBagConstraints gbc;
    /**
     * Label for the user's name.
     */
    JLabel name;
    /**
     * Label for the user's balance.
     */
    JLabel balance;
    /**
     * {@link Navigation} object for the navigation bar.
     */
    Navigation navigation;
    /**
     * {@link GridBagLayout} for the main screen.
     */
    GridBagLayout gbLayout;
    /**
     * Button to switch to the {@link Deposit} screen.
     */
    JButton deposit;
    /**
     * Button to switch to the {@link Send} screen.
     */
    JButton send;
    /**
     * Panel that contains the transactions.
     */
    Transactions transactions;
    /**
     * Scroll pane for the {@link #transactions} panel.
     */
    JScrollPane transactionsScroll;

    /**
     * Constructs a Main object.
     */
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

    /**
     * Updates the UI components with the current user's information.
     */
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

    /**
     * Handles the action of the deposit button.
     * This method is called when the deposit button is clicked.
     * It switches the view to the deposit screen.
     * @param e the event that triggered the action
     */
    private void deposit(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "deposit");
    }

    /**
     * Handles the action of the send button.
     * This method is called when the send button is clicked.
     * It switches the view to the send screen.
     * @param e the event that triggered the action
     */
    private void send(ActionEvent e) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "send");
    }

    /**
     * Inner class representing the transactions panel.
     * It displays the user's transactions.
     * <br><br>
     * It creates a {@link JPanel} for each transaction and adds them to itself.
     * Each transaction panel contains the title, value, and sender or destination of the transaction.
     * <br><br>
     * The <code>Transactions</code> object is created in the constructor and added to a {@link JScrollPane}.
     */
    class Transactions extends JPanel {
        /**
         * {@link User} object representing the current user.
         */
        User currentUser;
        /**
         * {@link GridBagLayout} for the transactions panel.
         */
        GridBagLayout gbLayout;
        /**
         * {@link GridBagConstraints} object for layout management.
         */
        GridBagConstraints gridBagConstraints;
        /**
         * Panel for each transaction.
         */
        JPanel transaction;
        /**
         * Label for the title of the transactions panel.
         */
        JLabel caption;
        /**
         * Label for the title of the transaction.
         */
        JLabel title;
        /**
         * Label for the value of the transaction.
         */
        JLabel value;
        /**
         * Label for the sender of the transaction.
         */
        JLabel sender;
        /**
         * Label for the destination of the transaction.
         */
        JLabel destination;

        /**
         * Constructs a Transactions object.
         */
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
