import java.util.ArrayList;
import java.util.List;

public class SpaceShipController implements Runnable {
    private List<SpaceShip> ships = new ArrayList<>();
    private int count;
    private Thread thread;
    private GamePanel jp;

    public SpaceShipController(int count, GamePanel panel) {
        this.count = count;
        jp = panel;
        initialize();
    }

    public List<SpaceShip> getShips() {
        return ships;
    }

    private void initialize() {
        thread = new Thread(this);
        thread.start();
    }

    private void initializeShips() {
        for (int i = 0; i < count; i++) {
            SpaceShip s = new SpaceShip();
            double width = jp.getBounds().getWidth();
            double height = jp.getBounds().getHeight();
            s.setX(Math.random() * width);
            s.setY(Math.random() * height);
            ships.add(s);
        }

    }

    private double moveAway(double me, double him) {
        double newValue = Math.random() * stepinterval - stepinterval / 2;
        if (me > him) {
            newValue += 1;
        } else {
            newValue -= 1;
        }
        return me + newValue;
    }

    private double moveTowards(double me, double him) {
        double newValue = Math.random() * stepinterval - stepinterval / 2;
        if (me > him) {
            newValue -= 1;
        } else {
            newValue += 1;
        }
        return me + newValue;
    }

    private void moveShips() {
        SpaceShip badGuy = ships.get(0);
        badGuy.setX(newX(badGuy.getX()));
        badGuy.setY(newY(badGuy.getY()));
        SpaceShip goodGuy = ships.get(1);
        goodGuy.setX(newX(goodGuy.getX()));
        goodGuy.setY(newY(goodGuy.getY()));
        for (int i = 2; i < ships.size(); i++) {
            SpaceShip ship = ships.get(i);
            ship.setX(moveAway(ship.getX(), badGuy.getX()));
            ship.setY(moveAway(ship.getY(), badGuy.getY()));
            ship.setX(moveTowards(ship.getX(), goodGuy.getX()));
            ship.setY(moveTowards(ship.getY(), goodGuy.getY()));
        }
    }

    private int stepinterval = 10;

    private double newX(double d) {
        double w = jp.getBounds().getWidth();
        double x = d + randomRange(stepinterval);
        if (x > w) {
            x = w;
        } else if (x < 0) {
            x = 0;
        }
        return x;
    }

    /**
     * Returns a random number +- 1/2 r
     * 
     * @param r
     * @return
     */
    private double randomRange(double r) {
        return (double) (Math.random() * r - .5 * r);
    }

    private double newY(double d) {
        double h = jp.getBounds().getHeight();
        double y = d + randomRange(10);
        if (y > h) {
            y = h;
        } else if (y < 0) {
            y = 0;
        }
        return y;
    }

    private void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }

    }

    private void redraw() {
        jp.repaint();
    }

    long turnLength = 100; // ms

    /**
     * Executed on a separate thread after the controller is constructed
     */
    @Override
    public void run() {
        pause(500);
        initializeShips();
        while (true) {
            long time = System.currentTimeMillis();
            moveShips();
            redraw();
            time = System.currentTimeMillis() - time;
            System.out.println(time);
            pause(turnLength - time);
        }
    }

}
