package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Navigation class represents a navigation panel with buttons for the {@link Main}, {@link Jars}, and {@link Settings} screens.
 */
public class Navigation extends JPanel{
    /**
     * Button to navigate to the {@link Main} screen.
     */
    JButton homeBtn;
    /**
     * Button to navigate to the {@link Jars} screen.
     */
    JButton jarsBtn;
    /**
     * Button to navigate to the {@link Settings} screen.
     */
    JButton settingsBtn;

    /**
     * Constructs a Navigation object.
     */
    Navigation() {
        homeBtn = new JButton("Home", new ImageIcon(getClass().getResource("assets/home.png")));
        jarsBtn = new JButton("Jars", new ImageIcon(getClass().getResource("assets/jars.png")));
        settingsBtn = new JButton("Settings", new ImageIcon(getClass().getResource("assets/settings.png")));

        Dimension btnSize = new Dimension(200, 60);
        homeBtn.setPreferredSize(btnSize);
        homeBtn.setFocusable(false);
        homeBtn.addActionListener(this::homeHandler);
        jarsBtn.setPreferredSize(btnSize);
        jarsBtn.setFocusable(false);
        jarsBtn.addActionListener(this::jarsHandler);
        settingsBtn.setPreferredSize(btnSize);
        settingsBtn.setFocusable(false);
        settingsBtn.addActionListener(this::settingsHandler);

        this.add(homeBtn);
        this.add(jarsBtn);
        this.add(settingsBtn);
    }

    /**
     * Handles the action of the home button.
     * This method is called when the home button is clicked.
     * It switches the layout to show the main screen.
     * @param event the event that triggered the action
     */
    void homeHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "main");
    }

    /**
     * Handles the action of the jars button.
     * This method is called when the jars button is clicked.
     * It switches the layout to show the jars screen.
     * @param event the event that triggered the action
     */
    void jarsHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "jars");
    }

    /**
     * Handles the action of the settings button.
     * This method is called when the settings button is clicked.
     * It switches the layout to show the settings screen.
     * @param event the event that triggered the action
     */
    void settingsHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "settings");
    }
}
