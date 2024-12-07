import javax.swing.*;
import java.awt.*;

public class Jars extends JPanel {
    JLabel test;
    BorderLayout bLayout;
    Navigation navigation;
    Jars() {
        bLayout = new BorderLayout();
        this.setLayout(bLayout);
        test = new JLabel("This is Jars");
        navigation = new Navigation();

        this.add(test);
        this.add(navigation, BorderLayout.SOUTH);
    }
}
