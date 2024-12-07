import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    Container cPane;
    CardLayout cLayout;
    Main main;
    Jars jars;
    Settings settings;
    UserInterface() {
        cPane = getContentPane();
        cLayout = new CardLayout();
        cPane.setLayout(cLayout);

//        main = new Main();
//        jars = new Jars();
//        settings = new Settings();
//
//        cPane.add("main", main);
//        cPane.add("jars", jars);
//        cPane.add("settings", settings);
        cPane.add(new Login());
        this.setIconImage(new ImageIcon(getClass().getResource("assets/icon.png")).getImage());
    }
}
