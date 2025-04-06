package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;

/**
 * UserInterface class that extends {@link JFrame} to create a GUI for the application.
 * It uses a custom {@link BetterCardLayout} to manage different panels.
 */
public class UserInterface extends JFrame {
    /**
     * The {@link Container} object that holds the panels for the user interface.
     */
    Container cPane;
    /**
     * The {@link BetterCardLayout} object used to manage the layout of the panels.
     */
    BetterCardLayout cLayout;

    /**
     * Constructs a UserInterface object.
     */
    UserInterface() {
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        cPane = getContentPane();
        cLayout = new BetterCardLayout();
        cPane.setLayout(cLayout);

        cPane.add("login", new Login());
        cPane.add("signup", new SignUp());
        cPane.add("jars", new Jars());
        cPane.add("settings", new Settings());
        cPane.add("main", new Main());
        cPane.add("deposit", new Deposit());
        cPane.add("send", new Send());
        cPane.add("createJar", new CreateJar());
        cPane.add("depositJar", new DepositJar());
        cPane.add("withdrawJar", new WithdrawJar());
        this.setIconImage(new ImageIcon(getClass().getResource("assets/icon.png")).getImage());
    }
}
