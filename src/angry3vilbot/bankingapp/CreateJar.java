package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

/**
 * CreateJar class is a {@link JPanel} that allows the user to create a new jar.
 * It contains a title field, a goal field, and two buttons: create and cancel.
 * <ul><li>The title field is required, while the goal field is optional.</li>
 * <li>The title field must not be empty or only contain "For".</li>
 * <li>The goal field must be a positive number or empty.</li></ul>
 * If the user clicks the create button, it will call the {@link Api#createJar} method
 * to create a new jar.
 * If the user clicks the cancel button, it will revert the fields to their initial values
 * and switch the card back to the {@link Jars} panel.
 */
public class CreateJar extends Api {
    /**
     * {@link GridBagLayout} object used for layout management.
     */
    GridBagLayout gbLayout;
    /**
     * {@link GridBagConstraints} object used for layout management.
     */
    GridBagConstraints gbc;
    /**
     * Label that serves as the title of the panel.
     */
    JLabel title;
    /**
     * Label for the {@link #titleField} field.
     */
    JLabel titleLabel;
    /**
     * Label for the {@link #goalField} field.
     */
    JLabel goalLabel;
    /**
     * Field to enter the title of the jar.
     */
    JTextField titleField;
    /**
     * Field to enter the goal amount for the jar.
     */
    JTextField goalField;
    /**
     * Button to create a new jar.
     */
    JButton createJarBtn;
    /**
     * Button to revert the fields to their initial values and switch the card back to the {@link Jars} panel.
     */
    JButton cancelBtn;

    /**
     * Constructs a new CreateJar panel.
     */
    CreateJar() {
        gbLayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        this.setLayout(gbLayout);
        title = new JLabel("Create a Jar");
        title.setFont(new Font("Dialog", Font.BOLD, 32));
        titleLabel = new JLabel("Title");
        goalLabel = new JLabel("Goal (Optional)");
        titleField = new JTextField("For ");
        goalField = new JTextField();
        titleField.setColumns(15);
        goalField.setColumns(15);
        createJarBtn = new JButton("Create");
        cancelBtn = new JButton("Cancel");
        createJarBtn.addActionListener(this::createJarHandler);
        cancelBtn.addActionListener(this::cancelHandler);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(title, gbc);

        // Title Label
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        this.add(titleLabel, gbc);

        // Title Field
        gbc.gridx = 1;
        this.add(titleField, gbc);

        // Goal Label
        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(goalLabel, gbc);

        // Goal Field
        gbc.gridx = 1;
        this.add(goalField, gbc);

        // Cancel Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(cancelBtn, gbc);

        // Create Button
        gbc.gridx = 1;
        this.add(createJarBtn, gbc);
    }

    /**
     * Handles the action of creating a jar.
     * It validates the input fields and calls the {@link Api#createJar} method.
     * If the input is valid, it updates the user information and switches the card back to the {@link Jars} panel.
     * If the input is invalid, it shows an error message.
     * @param event The event that triggered the action
     */
    private void createJarHandler(ActionEvent event) {
        try {
            String titleData = "";
            Double goalData;

            // Check if the title is empty or only contains "For"
            if(titleField.getText().trim().isEmpty() || titleField.getText().trim().equals("For")) {
                JOptionPane.showMessageDialog(null, "Error: The title cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else{
                titleData = titleField.getText().trim();
            }
            // Check if the goal field has invalid characters if it isn't empty
            if (!goalField.getText().trim().isEmpty()) {
                if(!goalField.getText().matches("[0-9]+([.][0-9]{1,2})?") && !goalField.getText().equals("0")) {
                    JOptionPane.showMessageDialog(null, "Error: The goal field must be a positive number or be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    goalData = Double.valueOf(goalField.getText().trim());
                }
            }
            else {
                goalData = null;
            }

            createJar(titleData, goalData);
        }
        catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage() + "\n" + exception.getCause());
        }
        finally {
            // Update the user information
            try {
                update();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage() + "\n" + exception.getCause());
            }
            // Revert everything to initial values
            titleField.setText("For ");
            goalField.setText("");
            // Update the UI of Jars
            Jars jars = (Jars) getParent().getComponent(2);
            jars.updateUI();
            // Switch card back to Jars
            CardLayout layout = (CardLayout) getParent().getLayout();
            layout.show(getParent(), "jars");
        }
    }

    /**
     * Handles the action of canceling the jar creation.
     * It reverts the input fields to their initial values and switches the card back to the {@link Jars} panel.
     * @param event The event that triggered the action
     */
    private void cancelHandler(ActionEvent event) {
        // Revert everything to initial values
        titleField.setText("For ");
        goalField.setText("");
        // Switch card back to Jars
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "jars");
    }
}
