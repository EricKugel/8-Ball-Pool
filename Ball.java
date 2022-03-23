import java.awt.*;

public class Ball {
    public static final int RADIUS = 10;

    private Color color;

    private double x;
    private double y;
    private double dX;
    private double dY;

    public Ball(Color color, double x, double y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Ball() {
        this(Color.WHITE, Pool.WIDTH / 2, Pool.HEIGHT / 2);
        this.color = Color.WHITE;
        this.x = Pool.WIDTH / 4;
        this.y = Pool.HEIGHT / 2;
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

    public void collideWith(Ball other) {
        if (other.getVector().getMagnitude() > getVector().getMagnitude()) {
            other.collideWith(this);
            return;
        }

        x = other.getX() - Math.cos(getVector().getDirection()) * Ball.RADIUS * 2;
        y = other.getY() - Math.sin(getVector().getDirection()) * Ball.RADIUS * 2;
        
        double theta = Math.atan((other.getY() - y) / (other.getX() - x)) + (other.getX() - x < 0 ? Math.PI : 0);
        other.setVector(Vector.createVectorFromTrig(theta, getVector().getMagnitude() * Math.cos(theta)));
        setVector(Vector.createVectorFromTrig(theta + Math.PI / 2, getVector().getMagnitude() * Math.sin(theta)));
        
        // System.out.println(theta);
        // double speed1 = getVector().getMagnitude();
        // other.setVector(new Vector(speed1 * Math.cos(theta), speed1 * Math.sin(theta)));
        // if (theta == 0) {
        //     setVector(new Vector(0, 0));
        // } else {
        //     // setVector(new Vector());
        // }
    }

    public boolean isTouching(Ball other) {
        return Math.hypot(other.getX() - x, other.getY() - y) < Ball.RADIUS * 2;
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
        g.setColor(color);
        g.fillArc((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2, 0, 360);
    }
}
