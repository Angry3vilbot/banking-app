package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

/**
 * Jars class represents the Jars screen in the application.
 * It displays the user's jars and allows them to create new jars, deposit to jars, and withdraw from jars.
 */
public class Jars extends JPanel {
    /**
     * Title label for the Jars screen.
     */
    JLabel title;
    /**
     * {@link GridBagLayout} object used for layout management.
     */
    GridBagLayout gbLayout;
    /**
     * {@link GridBagConstraints} object used for layout management.
     */
    GridBagConstraints gbc;
    /**
     * {@link JScrollPane} Container that provides a scrollbar for the {@link #jars}.
     */
    JScrollPane jarsContainer;
    /**
     * {@link JarDisplay} Container for the jars.
     */
    JarDisplay jars;
    /**
     * Container for the buttons.
     */
    JPanel buttonsContainer;
    /**
     * Button to create a new jar.
     */
    JButton createJarBtn;
    /**
     * Button to deposit to a jar.
     */
    JButton depositToJarBtn;
    /**
     * {@link Navigation} object for navigating between screens.
     */
    Navigation navigation;

    /**
     * Constructs a Jars object.
     */
    Jars() {
        gbLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        this.setLayout(gbLayout);
        title = new JLabel("Jars");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        navigation = new Navigation();
        jars = new JarDisplay();
        jarsContainer = new JScrollPane(jars, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jarsContainer.setBorder(null);
        jarsContainer.setPreferredSize(new Dimension(300, 300));
        JScrollBar verticalScrollBar = jarsContainer.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);
        buttonsContainer = new JPanel();
        createJarBtn = new JButton("New Jar", new ImageIcon(getClass().getResource("assets/deposit.png")));
        createJarBtn.addActionListener(this::createJarHandler);
        Dimension btnSize = new Dimension(200, 60);
        createJarBtn.setPreferredSize(btnSize);
        createJarBtn.setFocusable(false);
        depositToJarBtn = new JButton("Deposit to Jar", new ImageIcon(getClass().getResource("assets/depositJar.png")));
        depositToJarBtn.setPreferredSize(btnSize);
        depositToJarBtn.setFocusable(false);
        depositToJarBtn.addActionListener(this::depositToJarHandler);

        // Title label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(title, gbc);

        // Buttons container
        gbc.gridy = 1;
        this.add(buttonsContainer, gbc);

        // Create Jar button
        buttonsContainer.add(createJarBtn, gbc);

        // Deposit to Jar button
        buttonsContainer.add(depositToJarBtn, gbc);

        // Jars container
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(jarsContainer, gbc);

        // Navigation
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(navigation, gbc);
    }

    /**
     * Handles the action event when the create jar button is clicked.
     * This method switches the view to the create jar screen.
     * @param event The action event triggered when the create jar button is clicked.
     */
    private void createJarHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "createJar");
    }

    /**
     * Handles the action event when the deposit to jar button is clicked.
     * This method switches the view to the deposit to jar screen.
     * @param event The action event triggered when the deposit to jar button is clicked.
     */
    private void depositToJarHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "depositJar");
    }

    /**
     * JarDisplay class represents the display of jars in the {@link Jars} screen.
     * It contains the jar image, title, value, progress bar, and buttons for each jar.
     * It also handles the actions for copying jar ID, depositing to jars, and withdrawing from jars.
     * It creates a new {@link JPanel} for each jar and adds it to itself.
     * The JarDisplay object is created in the {@link Jars} constructor and added to a {@link JScrollPane}.
     */
    private class JarDisplay extends JPanel {
        /**
         * {@link User} object representing the current user.
         */
        User currentUser;
        /**
         * {@link GridBagLayout} object used for layout management.
         */
        GridBagLayout gbagLayout;
        /**
         * {@link GridBagConstraints} object used for layout management.
         */
        GridBagConstraints gridBagConstraints;
        /**
         * {@link JLabel} for the jar's image.
         */
        JLabel jarImage;
        /**
         * {@link JPanel} for the jar.
         */
        JPanel jarPanel;
        /**
         * Label for the jar's title.
         */
        JLabel jarTitle;
        /**
         * Label for the jar's value.
         */
        JLabel jarValue;
        /**
         * {@link JProgressBar} for the jar's goal.
         */
        JProgressBar jarGoalProgress;
        /**
         * Container for the buttons.
         */
        JPanel buttonsContainer;
        /**
         * Button to copy the jar's ID.
         */
        JButton copyBtn;
        /**
         * Button to deposit into the jar.
         */
        JButton depositBtn;
        /**
         * Button to withdraw from the jar.
         */
        JButton withdrawBtn;

        /**
         * Constructs a JarDisplay object.
         */
        JarDisplay() {
            currentUser = UserSession.getInstance().getUser();
            gbagLayout = new GridBagLayout();
            gridBagConstraints = new GridBagConstraints();
            this.setLayout(gbagLayout);

            if(currentUser != null) {
                if(currentUser.getJars() == null || currentUser.getJars().length == 0) {
                    JLabel noJarsLabel = new JLabel("No jars found");
                    noJarsLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
                    noJarsLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    this.add(noJarsLabel);
                }
                for (int i = 0; i < currentUser.getJars().length; i++) {
                    jarPanel = new JPanel();
                    jarPanel.setPreferredSize(new Dimension(500, 100));
                    jarPanel.setMaximumSize(new Dimension(500, 100));
                    jarPanel.setMinimumSize(new Dimension(500, 100));
                    GridBagLayout jgbl = new GridBagLayout();
                    GridBagConstraints jgbc = new GridBagConstraints();
                    jarPanel.setLayout(jgbl);
                    // Jar Image
                    jarImage = new JLabel(new ImageIcon(getClass().getResource("assets/jars.png")));
                    jarImage.setPreferredSize(new Dimension(100,100));
                    jgbc.fill = GridBagConstraints.NONE;
                    jgbc.gridx = 0;
                    jgbc.gridy = 0;
                    jgbc.gridheight = 3;
                    jgbc.weightx = 0;
                    jarPanel.add(jarImage, jgbc);
                    // Jar Title
                    jarTitle = new JLabel(currentUser.getJars()[i].getTitle());
                    jarTitle.setPreferredSize(new Dimension(200, 30));
                    jgbc.fill = GridBagConstraints.HORIZONTAL;
                    jgbc.insets = new Insets(0, 10, 10, 0);
                    jgbc.gridx = 1;
                    jgbc.gridheight = 1;
                    jgbc.weightx = 1;
                    jgbc.anchor = GridBagConstraints.LINE_START;
                    jarPanel.add(jarTitle, jgbc);
                    // Jar goal progress bar and value
                    if (currentUser.getJars()[i].getGoal() != 0.0) {
                        jarValue = new JLabel(currentUser.getJars()[i].getBalance() + " € / " + currentUser.getJars()[i].getGoal() + " €");
                        jarGoalProgress = new JProgressBar(0, (int) Math.round(currentUser.getJars()[i].getGoal()));
                        jarGoalProgress.setValue((int) Math.round(currentUser.getJars()[i].getBalance()));
                        jarGoalProgress.setPreferredSize(new Dimension(200, 20));
                        jgbc.gridwidth = 2;
                        jgbc.gridy = 1;
                        jgbc.gridx = 1;
                        jgbc.anchor = GridBagConstraints.CENTER;
                        jgbc.fill = GridBagConstraints.HORIZONTAL;
                        jarPanel.add(jarGoalProgress, jgbc);
                    }
                    else {
                        jarValue = new JLabel(currentUser.getJars()[i].getBalance() + " €");
                    }
                    jarValue.setPreferredSize(new Dimension(200, 30));
                    jarValue.setHorizontalAlignment(SwingConstants.RIGHT);
                    jgbc.fill = GridBagConstraints.HORIZONTAL;
                    jgbc.gridwidth = 1;
                    jgbc.gridx = 2;
                    jgbc.gridy = 0;
                    jgbc.weightx = 1;
                    jarPanel.add(jarValue, jgbc);
                    // Buttons Container
                    buttonsContainer = new JPanel();
                    buttonsContainer.setLayout(new GridBagLayout());
                    jgbc.gridx = 1;
                    jgbc.gridy = 2;
                    jgbc.gridwidth = 2;
                    jgbc.weightx = 1;
                    jarPanel.add(buttonsContainer, jgbc);
                    // Copy ID Button
                    copyBtn = new JButton("Copy ID");
                    copyBtn.setFocusable(false);
                    copyBtn.putClientProperty("id", currentUser.getJars()[i].getId());
                    copyBtn.addActionListener(this::copyId);
                    jgbc.gridx = 0;
                    jgbc.gridy = 0;
                    jgbc.gridwidth = 1;
                    jgbc.weightx = 1;
                    jgbc.anchor = GridBagConstraints.LINE_START;
                    buttonsContainer.add(copyBtn, jgbc);
                    // Deposit Button
                    depositBtn = new JButton("Deposit");
                    depositBtn.setFocusable(false);
                    depositBtn.putClientProperty("id", currentUser.getJars()[i].getId());
                    depositBtn.addActionListener(this::depositHandler);
                    jgbc.gridx = 1;
                    jgbc.anchor = GridBagConstraints.CENTER;
                    buttonsContainer.add(depositBtn, jgbc);
                    // Withdraw Button
                    withdrawBtn = new JButton("Withdraw");
                    withdrawBtn.setFocusable(false);
                    withdrawBtn.addActionListener(this::withdrawHandler);
                    withdrawBtn.putClientProperty("id", currentUser.getJars()[i].getId());
                    withdrawBtn.putClientProperty("title", currentUser.getJars()[i].getTitle());
                    withdrawBtn.putClientProperty("value", currentUser.getJars()[i].getBalance());
                    jgbc.gridx = 2;
                    jgbc.anchor = GridBagConstraints.LINE_END;
                    buttonsContainer.add(withdrawBtn, jgbc);

                    gridBagConstraints.gridy = i;
                    this.add(jarPanel, gridBagConstraints);
                }
            }
       }

        /**
         * Handles the action event when the copy ID button is clicked.
         * This method copies the jar ID to the clipboard.
         * @param event The action event triggered when the copy ID button is clicked.
         */
        private void copyId(ActionEvent event) {
            int id = (int)((JButton)event.getSource()).getClientProperty("id");
            StringSelection selection = new StringSelection(id + "");
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        }

        /**
         * Handles the action event when the deposit button is clicked.
         * This method switches the view to the deposit jar screen.
         * @param event The action event triggered when the deposit button is clicked.
         */
        private void depositHandler(ActionEvent event) {
            DepositJar depositJar = (DepositJar) getParent().getParent().getParent().getParent().getComponent(8);
            depositJar.idField.setText(((JButton)event.getSource()).getClientProperty("id") + "");
            CardLayout layout = (CardLayout) getParent().getParent().getParent().getParent().getLayout();
            layout.show(getParent().getParent().getParent().getParent(), "depositJar");
        }

        /**
         * Handles the action event when the withdraw button is clicked.
         * This method switches the view to the withdraw from jar screen.
         * @param event The action event triggered when the withdraw button is clicked.
         */
        private void withdrawHandler(ActionEvent event) {
            // WithdrawJar screen
            WithdrawJar withdrawJar = (WithdrawJar) getParent().getParent().getParent().getParent().getComponent(9);
            // Button clicked
            JButton button = (JButton) event.getSource();
            // Set the title and ID of the jar to withdraw from
            withdrawJar.setTitleAndID((String) button.getClientProperty("title"), (Integer) button.getClientProperty("id"));
            // Set the amount field label
            withdrawJar.setAmountLabel(button.getClientProperty("value") + " €");
            // Show the WithdrawJar screen
            CardLayout layout = (CardLayout) getParent().getParent().getParent().getParent().getLayout();
            layout.show(getParent().getParent().getParent().getParent(), "withdrawJar");
        }
    }

    /**
     * Updates the UI components with the current user's information.
     */
    public void updateUI() {
        User user = UserSession.getInstance().getUser();
        // If logged in
        if(user != null) {
            if(jarsContainer != null) {
                this.remove(jarsContainer);
            }
            jars = new JarDisplay();
            // updateUI method is overridden to remove the border
            jarsContainer = new JScrollPane(jars, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER){
                @Override
                public void updateUI() {
                    super.updateUI();
                    setBorder(null);
                }
            };
            jarsContainer.setPreferredSize(new Dimension(300, 500));
            JScrollBar verticalScrollBar = jarsContainer.getVerticalScrollBar();
            verticalScrollBar.setUnitIncrement(16);

            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            this.add(jarsContainer, gbc);
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
}
