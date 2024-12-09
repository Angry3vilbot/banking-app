import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    JLabel test;
    Navigation navigation;
    BorderLayout bLayout;
    Main() {
        User currentUser = UserSession.getInstance().getUser();
        bLayout = new BorderLayout();
        this.setLayout(bLayout);
        if(currentUser != null) {
            test = new JLabel(currentUser.getBalance() + " €", SwingConstants.CENTER);
            test.setFont(new Font("Dialog", Font.BOLD, 24));
            this.add(test);
        }
        navigation = new Navigation();

        this.add(navigation, BorderLayout.SOUTH);
    }

    public void updateUI() {
        User currentUser = UserSession.getInstance().getUser();
        // If logged in
        if(currentUser != null) {
            if(test != null) {
                this.remove(test);
            }
            test = new JLabel(currentUser.getBalance() + " €", SwingConstants.CENTER);
            test.setFont(new Font("Dialog", Font.BOLD, 24));
            this.add(test, BorderLayout.CENTER);
        }
        this.revalidate();
        this.repaint();
    }
}
