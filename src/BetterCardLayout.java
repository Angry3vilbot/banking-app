import javax.swing.*;
import java.awt.CardLayout;
import java.awt.Container;

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