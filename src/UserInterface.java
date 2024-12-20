import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    Container cPane;
    CardLayout cLayout;
    UserInterface() {
        cPane = getContentPane();
        cLayout = new CardLayout();
        cPane.setLayout(cLayout);

        cPane.add("login", new Login());
        cPane.add("jars", new Jars());
        cPane.add("settings", new Settings());
        cPane.add("main", new Main());
        cPane.add("deposit", new Deposit());
        this.setIconImage(new ImageIcon(getClass().getResource("assets/icon.png")).getImage());
    }
}
