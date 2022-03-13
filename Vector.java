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
}
