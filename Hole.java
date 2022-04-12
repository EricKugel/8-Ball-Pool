import java.awt.Graphics;
import java.awt.Color;

public class Hole {
    public static final int RADIUS = 20;

    private double x;
    private double y;

    public Hole(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillArc((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2, 0, 360);
    }   
}
