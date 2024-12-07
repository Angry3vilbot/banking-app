import javax.swing.*;
import java.awt.*;

public class Settings extends JPanel {
    JLabel test;
    BorderLayout bLayout;
    Navigation navigation;
    Settings() {
        bLayout = new BorderLayout();
        this.setLayout(bLayout);
        test = new JLabel("This is settings");
        navigation = new Navigation();

        this.add(test);
        this.add(navigation, BorderLayout.SOUTH);
    }
}
