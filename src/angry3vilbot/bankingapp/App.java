package angry3vilbot.bankingapp;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Main class for the Banking Application.
 * This class sets up the GUI and redirects error output to a file.
 * It also sets the look and feel of the application.
 */
public class App implements Runnable {
    /**
     * Main method to start the application.
     * Redirects {@link System#err} to a file and sets the look and feel. Then it invokes the GUI.
     * @param args command line arguments
     */
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

    /**
     * Run method to set up the GUI.
     * This method is called on the Event Dispatch Thread (EDT).
     * It initializes the User Interface and sets its properties.
     */
    @Override
    public void run() {
        UserInterface ui = new UserInterface();
        ui.setTitle("Banking App");
        ui.setSize(1000, 500);
        ui.setVisible(true);
        ui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
