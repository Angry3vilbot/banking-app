import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Deposit extends Api {
    JRadioButton freeMoney;
    JRadioButton fromCard;
    JPanel radioContainer;
    ButtonGroup paymentMethod;
    JTextField paymentInfo;
    JTextField depositAmount;
    JLabel paymentInfoLabel;
    JLabel depositAmountLabel;
    JButton submitBtn;
    JButton cancelBtn;

    Deposit() {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        radioContainer = new JPanel();
        freeMoney = new JRadioButton("Free Money", true);
        fromCard = new JRadioButton("Card Number");
        paymentMethod = new ButtonGroup();
        paymentInfo = new JTextField();
        depositAmount = new JTextField();
        paymentInfoLabel = new JLabel("Payment Info");
        depositAmountLabel = new JLabel("Amount");
        submitBtn = new JButton("Deposit");
        cancelBtn = new JButton("Cancel");

        radioContainer.add(freeMoney);
        radioContainer.add(fromCard);
        paymentMethod.add(freeMoney);
        paymentMethod.add(fromCard);
        paymentInfo.setColumns(15);
        depositAmount.setColumns(15);
        submitBtn.addActionListener(this::submitBtnHandler);
        cancelBtn.addActionListener(this::cancelBtnHandler);
        this.setLayout(grid);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(radioContainer, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        this.add(paymentInfoLabel, gbc);

        gbc.gridx = 1;
        this.add(paymentInfo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(depositAmountLabel, gbc);

        gbc.gridx = 1;
        this.add(depositAmount, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(cancelBtn, gbc);

        gbc.gridx = 1;
        this.add(submitBtn, gbc);
    }

    private void submitBtnHandler(ActionEvent e) {

    }

    private void cancelBtnHandler(ActionEvent e) {
        // Revert everything to initial values
        paymentMethod.clearSelection();
        paymentMethod.setSelected(freeMoney.getModel(), true);
        paymentInfo.setText("");
        depositAmount.setText("");
        // Switch card back to Main
        CardLayout layout = (CardLayout) getParent().getLayout();
        layout.show(getParent(), "main");
    }
}
