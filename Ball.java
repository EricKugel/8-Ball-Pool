import java.awt.*;

public class Ball {
    public static final int RADIUS = 10;

    private double x;
    private double y;
    private double dX;
    private double dY;

    public Ball() {
        this.x = Pool.WIDTH / 2;
        this.y = Pool.HEIGHT / 2;
        this.dX = 0;
        this.dY = 0;
    }

    public void update() {
        x += dX;
        y += dY;

        if (x - RADIUS < 0) {
            x = RADIUS;
            dX *= -.75;
        } if (x + RADIUS >= Pool.WIDTH) {
            x = Pool.WIDTH - RADIUS - 1;
            dX *= -.75;
        } if (y - RADIUS < 0) {
            y = RADIUS;
            dY *= -.75;
        } if (y + RADIUS >= Pool.HEIGHT) {
            y = Pool.HEIGHT - RADIUS - 1;
            dY *= -.75;
        }

        Vector vector = getVector();
        double newSpeed = vector.getMagnitude() -.05;
        newSpeed = newSpeed < 0 ? 0 : newSpeed;
        setVector(Vector.createVectorFromTrig(vector.getDirection(), newSpeed));
    }

    public Vector getVector() {
        return new Vector(dX, dY);
    }

    public void setVector(Vector vector) {
        dX = vector.getX();
        dY = vector.getY();
    }

    public boolean isMoving() {
        return dX != 0 || dY != 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillArc((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2, 0, 360);
    }
}
