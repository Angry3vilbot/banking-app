import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

public class CreateJar extends Api {
    GridBagLayout gbLayout;
    GridBagConstraints gbc;
    JLabel title;
    JLabel titleLabel;
    JLabel goalLabel;
    JTextField titleField;
    JTextField goalField;
    JButton createJarBtn;
    JButton cancelBtn;

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
        createJarBtn = new JButton("Create");
        cancelBtn = new JButton("Cancel");
        createJarBtn.addActionListener(this::createJarHandler);
        cancelBtn.addActionListener(this::cancelHandler);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(title, gbc);

        // Title Label
        gbc.gridy = 1;
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

    private void createJarHandler(ActionEvent event) {
        try {
            String titleData = "";
            Double goalData;

            // Check if the title is empty
            if(titleField.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Error: The title cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else{
                titleData = titleField.getText().trim();
            }
            // Check if the goal field has invalid characters if it isn't empty
            if (!goalField.getText().trim().isEmpty()) {
                if(!goalField.getText().matches("[0-9]+[.]?[0-9]+") && !goalField.getText().equals("0")) {
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

    private void cancelHandler(ActionEvent event) {
        // Revert everything to initial values
        titleField.setText("For ");
        goalField.setText("");
        // Switch card back to Jars
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "jars");
    }
}
