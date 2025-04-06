package angry3vilbot.bankingapp;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/**
 * Settings class represents the settings panel of the application.
 * It allows users to change the theme of the application and log out.
 */
public class Settings extends JPanel {
    /**
     * {@link GridBagLayout} object for layout management.
     */
    GridBagLayout gbLayout;
    /**
     * {@link GridBagConstraints} object for layout management.
     */
    GridBagConstraints gbc;
    /**
     * Panel for holding the options in the settings panel.
     */
    JPanel options;
    /**
     * Label for the title of the settings panel.
     */
    JLabel title;
    /**
     * Label for the theme selector.
     */
    JLabel themeLabel;
    /**
     * {@link JComboBox} for selecting the theme of the application.
     */
    JComboBox<String> themeSelector;
    /**
     * Button to log out of the application.
     */
    JButton logOutBtn;
    /**
     * Navigation object for navigating between different panels.
     */
    Navigation navigation;
    /**
     * Array of available themes for the application.
     */
    String[] themes = {"Light", "Dark", "Darkula", "IntelliJ"};

    /**
     * Constructs a Settings object.
     */
    Settings() {
        gbLayout = new GridBagLayout();
        this.setLayout(gbLayout);
        gbc = new GridBagConstraints();
        options = new JPanel();
        options.setLayout(new GridBagLayout());
        title = new JLabel("Settings");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        themeLabel = new JLabel("Theme:");
        themeLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
        navigation = new Navigation();
        themeSelector = new JComboBox<>(themes);
        logOutBtn = new JButton("Log Out");
        themeSelector.addItemListener(this::changeTheme);
        logOutBtn.addActionListener(this::logOutBtnHandler);

        // Title label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(title, gbc);

        // Options container
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(options, gbc);

        // Theme selector label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        options.add(themeLabel, gbc);

        // Theme selector
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        options.add(themeSelector, gbc);

        // Log out button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        options.add(logOutBtn, gbc);

        // Navigation
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(navigation, gbc);
    }

    /**
     * Change the theme of the application based on the user's selection.
     * This method is triggered when the user selects a theme from the combo box.
     * @param event the event triggered when the user selects a theme from the combo box
     */
    // Set the Look and Feel based on the user's selection
    private void changeTheme(ItemEvent event) {
        try {
            switch (event.getItem().toString()) {
                case "Light":
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                case "Dark":
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
                case "Darkula":
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                    break;
                case "IntelliJ":
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    break;
            }
            // Update the UI to refresh LaF
            SwingUtilities.updateComponentTreeUI(this.getParent().getParent().getParent());
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the log out button click event.
     * Logs out the user and clear the session.
     * This method is triggered when the user clicks the log out button.
     * @param event the event triggered when the user clicks the log out button
     */
    private void logOutBtnHandler(ActionEvent event) {
        UserSession.getInstance().clearSession();
        CardLayout layout = (CardLayout) this.getParent().getLayout();
        layout.show(this.getParent(), "login");
    }
}
