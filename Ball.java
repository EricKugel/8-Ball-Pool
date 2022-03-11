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
        this.dX = 10;
        this.dY = 10;
    }

    public void update() {
        System.out.println("Updating");
        x += dX;
        y += dY;

        if (x - RADIUS < 0) {
            x = RADIUS;
            dX *= -.9;
        } if (x + RADIUS >= Pool.WIDTH) {
            x = Pool.WIDTH - RADIUS - 1;
            dX *= -.9;
        } if (y - RADIUS < 0) {
            y = RADIUS;
            dY *= -.9;
        } if (y + RADIUS >= Pool.HEIGHT) {
            y = Pool.HEIGHT - RADIUS - 1;
            dY *= -.9;
        }

        dX -= .05;
        dY -= .05;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillArc((int) x, (int) y, RADIUS * 2, RADIUS * 2, 0, 360);
    }
}
