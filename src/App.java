import javax.swing.*;

public class App {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.setTitle("Banking App (Working Title)");
        ui.setSize(1000, 500);
        ui.setVisible(true);
        ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
