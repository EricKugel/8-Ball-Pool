import java.awt.*;

public class Stick {
    private double x = 0;
    private double y = 0;
    private double direction = 0;
    private double distanceFromBall = 0;

    public static final int SIZE = 250;

    public static final int FULL_SPEED = 40;

    public Stick(double x, double y, double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.translate((int) x, (int) y);
        g2D.rotate(direction + Math.PI);

        int[] stripeLocations = {0, 5, SIZE / 2, SIZE / 2 + 5, SIZE};
        Color[] stripeColors = {Color.WHITE, new Color(148, 116, 0), Color.BLACK, new Color(148, 116, 0)};

        for (int i = 0; i < stripeColors.length; i++) {
            g2D.setColor(stripeColors[i]);
            g2D.fillRect(Ball.RADIUS + 2 + (int) distanceFromBall + stripeLocations[i], -2, stripeLocations[i + 1] - stripeLocations[i], 5);
        }
        
        g2D.translate(x * -1, y * -1);        
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
    
    public double getDirection() {
        return direction;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setDistanceFromBall(double distanceFromBall) {
        this.distanceFromBall = distanceFromBall;
    }
}
