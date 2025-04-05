import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class App implements Runnable {
    public static void main(String[] args) {
        // Redirect System.err to a file
        try {
            File errorLog = new File("error.log");
            FileOutputStream fileOutputStream = new FileOutputStream(errorLog, true);

            // Create a PrintStream that writes to the file
            PrintStream filePrintStream = new PrintStream(fileOutputStream);

            // Redirect System.err to the file
            System.setErr(filePrintStream);
        } catch (IOException e) {
            // Print the error to original stderr if file redirection fails
            e.printStackTrace();
            // Change the error stream to the console
            System.setErr(System.out);
        }
        // Set the look and feel to FlatLaf Light
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
        ui.setTitle("Banking App");
        ui.setSize(1000, 500);
        ui.setVisible(true);
        ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
