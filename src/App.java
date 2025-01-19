import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class App implements Runnable {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        SwingUtilities.invokeLater(new App());
    }

    @Override
    public void run() {
        UserInterface ui = new UserInterface();
        ui.setTitle("Banking App (Working Title)");
        ui.setSize(1000, 500);
        ui.setVisible(true);
        ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
