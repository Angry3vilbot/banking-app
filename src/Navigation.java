import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navigation extends JPanel{
    JButton homeBtn, jarsBtn, settingsBtn;
    Navigation() {
        homeBtn = new JButton("Home", new ImageIcon(getClass().getResource("home.png")));
        jarsBtn = new JButton("Jars", new ImageIcon(getClass().getResource("jars.png")));
        settingsBtn = new JButton("Settings", new ImageIcon(getClass().getResource("settings.png")));

        Dimension btnSize = new Dimension(200, 60);
        homeBtn.setPreferredSize(btnSize);
        homeBtn.setFocusable(false);
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeHandler();
            }
        });
        jarsBtn.setPreferredSize(btnSize);
        jarsBtn.setFocusable(false);
        jarsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jarsHandler();
            }
        });
        settingsBtn.setPreferredSize(btnSize);
        settingsBtn.setFocusable(false);
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsHandler();
            }
        });

        this.add(homeBtn);
        this.add(jarsBtn);
        this.add(settingsBtn);
    }

    void homeHandler() {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "main");
    }
    void jarsHandler() {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "jars");
    }
    void settingsHandler() {
        CardLayout layout = (CardLayout) getParent().getParent().getLayout();
        layout.show(getParent().getParent(), "settings");
    }
}
