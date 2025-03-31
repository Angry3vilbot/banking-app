import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    Container cPane;
    CardLayout cLayout;
    UserInterface() {
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        cPane = getContentPane();
        cLayout = new CardLayout();
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
