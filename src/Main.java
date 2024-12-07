import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    JLabel test;
    Navigation navigation;
    BorderLayout bLayout;
    Main() {
        bLayout = new BorderLayout();
        this.setLayout(bLayout);
        test = new JLabel("268.31" + " €", SwingConstants.CENTER);
        test.setFont(new Font("Dialog", Font.BOLD, 24));
        navigation = new Navigation();

        this.add(test);
        this.add(navigation, BorderLayout.SOUTH);
    }
}
