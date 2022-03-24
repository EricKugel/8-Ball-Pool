public class Vector {
    private double x = 0;
    private double y = 0;
    private double direction = 0;
    private double magnitude = 0;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.direction = (x != 0) ? (Math.atan(y / x) + (x < 0 ? Math.PI : 0)) : 0;
        this.magnitude = Math.hypot(x, y);
    }

    public static Vector createVectorFromTrig(double direction, double magnitude) {
        return new Vector(Math.cos(direction) * magnitude, Math.sin(direction) * magnitude);
    }

    public static Vector scaleVector(Vector vector, double scalar) {
        return new Vector(vector.getX() * scalar, vector.getY() * scalar);
    }

    public static Vector createUnitVector(Vector vector) {
        return scaleVector(vector, (1 / vector.getMagnitude()));
    }

    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    public static double dotProduct(Vector u, Vector v) {
        return u.getX() * v.getX() + u.getY() * v.getY();
    }

    public static Vector proj(Vector u, Vector v) {
        return scaleVector(v, dotProduct(u, v) / Math.pow(v.getMagnitude(), 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public double getMagnitude() {
        return magnitude;
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + ">";
    }

    public static void main (String[] arg0) {
        Vector u = new Vector(3, 4);
        Vector v = new Vector(5, -12);
        // System.out.println(proj(u, v));
    }
}
