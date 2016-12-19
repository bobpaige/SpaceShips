import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private SpaceShipController ssc = null;

    public GamePanel() {
        ssc = new SpaceShipController(25, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(10, 10, 20, 15);

        for (SpaceShip ship : ssc.getShips()) {
            g.drawRect((int) ship.getX(), (int) ship.getY(), 10, 10);
        }

    }

}
