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

        dX -= .02;
        dY -= .02;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillArc((int) x, (int) y, RADIUS * 2, RADIUS * 2, 0, 360);
    }
}
