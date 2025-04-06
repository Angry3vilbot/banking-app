package angry3vilbot.bankingapp;

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.Container;

/**
 * A custom CardLayout that ensures the layout is updated correctly on different display scales.
 * <br><br>
 * This class extends the default CardLayout and overrides the show method to revalidate and repaint the parent container.
 * <br><br>
 * This is useful for ensuring that the layout is displayed correctly on high-DPI displays or when the display scale changes.
 */
public class BetterCardLayout extends CardLayout {
    @Override
    public void show(Container parent,String name) {
        super.show(parent,name);
        // revalidate and repaint the parent container to ensure that the layout is updated correctly on different display scales
        SwingUtilities.invokeLater(() -> {
            parent.getParent().revalidate();
            parent.getParent().repaint();
        });
    }
}