import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class Jars extends JPanel {
    JLabel title;
    GridBagLayout gbLayout;
    GridBagConstraints gbc;
    JScrollPane jarsContainer;
    JarDisplay jars;
    JPanel buttonsContainer;
    JButton createJarBtn;
    JButton depositToJarBtn;
    Navigation navigation;
    Jars() {
        gbLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        this.setLayout(gbLayout);
        title = new JLabel("Jars");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        navigation = new Navigation();
        jars = new JarDisplay();
        jarsContainer = new JScrollPane(jars, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

    private void createJarHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "createJar");
    }

    private void depositToJarHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "depositJar");
    }

    private class JarDisplay extends JPanel {
        User currentUser;
        GridBagLayout gbagLayout;
        GridBagConstraints gridBagConstraints;
        JLabel jarImage;
        JPanel jarPanel;
        JLabel jarTitle;
        JLabel jarValue;
        JProgressBar jarGoalProgress;
        JPanel buttonsContainer;
        JButton copyBtn;
        JButton depositBtn;
        JButton withdrawBtn;
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

        private void copyId(ActionEvent event) {
            int id = (int)((JButton)event.getSource()).getClientProperty("id");
            StringSelection selection = new StringSelection(id + "");
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        }
        private void depositHandler(ActionEvent event) {
            DepositJar depositJar = (DepositJar) getParent().getParent().getParent().getParent().getComponent(8);
            depositJar.idField.setText(((JButton)event.getSource()).getClientProperty("id") + "");
            CardLayout layout = (CardLayout) getParent().getParent().getParent().getParent().getLayout();
            layout.show(getParent().getParent().getParent().getParent(), "depositJar");
        }
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

    public void updateUI() {
        User user = UserSession.getInstance().getUser();
        // If logged in
        if(user != null) {
            if(jarsContainer != null) {
                this.remove(jarsContainer);
            }
            jars = new JarDisplay();
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
