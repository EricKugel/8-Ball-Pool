import java.awt.*;

public class Ball {
    public static final int RADIUS = 20;

    private Color color;

    private double x;
    private double y;
    private double dX;
    private double dY;

    private Ball lastHit = null;

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
        Vector collisionVector = new Vector(x - other.getX(), y - other.getY());
        double distance = collisionVector.getMagnitude();
        x += (x - other.getX()) * (((Ball.RADIUS * 2) - distance) / distance) * 0.5;
        y += (y - other.getY()) * (((Ball.RADIUS * 2) - distance) / distance) * 0.5;
        other.setX(other.getX() - (x - other.getX()) * (((Ball.RADIUS * 2) - distance) / distance) * 0.5);
        other.setY(other.getY() - (y - other.getY()) * (((Ball.RADIUS * 2) - distance) / distance) * 0.5);

        Vector unitCollisionVector = Vector.createUnitVector(collisionVector);
        Vector unitTangentVector = new Vector(unitCollisionVector.getY() * -1, unitCollisionVector.getX());

        double collisionVelocity1 = Vector.dotProduct(getVector(), unitCollisionVector);
        double tangentVelocity1 = Vector.dotProduct(getVector(), unitTangentVector);
        double collisionVelocity2 = Vector.dotProduct(other.getVector(), unitCollisionVector);
        double tangentVelocity2 = Vector.dotProduct(other.getVector(), unitTangentVector);
        
        setVector(Vector.add(Vector.scaleVector(unitTangentVector, tangentVelocity2), Vector.scaleVector(unitTangentVector, tangentVelocity1)));
        other.setVector(Vector.add(Vector.scaleVector(unitCollisionVector, collisionVelocity2), Vector.scaleVector(unitCollisionVector, collisionVelocity1)));
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void draw(Graphics g) {
        for (int x = RADIUS * -1; x < RADIUS; x++) {
            for (int y = RADIUS * -1; y < RADIUS; y++) {
                if (Math.hypot(x, y) <= RADIUS) {
                    double direction = Math.PI / 2;
                    double relativeX = x - RADIUS / -2;
                    double relativeY = y - RADIUS / -2;
                    if (relativeX != 0) {
                        direction = (Math.atan(relativeY / relativeX));
                        if (relativeX < 0) {
                            direction += Math.PI;
                        }
                    }
                    double maxDistance = Math.hypot(RADIUS * Math.cos(direction) - Ball.RADIUS / 2 * -1, RADIUS * Math.sin(direction) - Ball.RADIUS / 2 * -1);
                    double brightnessScale = Math.hypot(x - Ball.RADIUS / 2 * -1, y - Ball.RADIUS / 2 * -1) / maxDistance;
                    int red = (int) (255 * (1 - brightnessScale) + color.getRed() * brightnessScale);
                    int green = (int) (255 * (1 - brightnessScale) + color.getGreen() * brightnessScale);
                    int blue = (int) (255 * (1 - brightnessScale) + color.getBlue() * brightnessScale);
                    g.setColor(new Color(red, green, blue));
                    g.drawLine((int) (x + this.x), (int) (y + this.y), (int) (x + this.x), (int) (y + this.y));
                }
            }
        }
    }
}