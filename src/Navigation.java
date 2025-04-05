import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Navigation extends JPanel{
    JButton homeBtn, jarsBtn, settingsBtn;
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

    void homeHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "main");
    }
    void jarsHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "jars");
    }
    void settingsHandler(ActionEvent event) {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "settings");
    }
}
