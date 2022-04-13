import java.awt.*;

public class Ball {
    public static final int RADIUS = 7;

    private Color color;

    private double x;
    private double y;
    private double dX;
    private double dY;

    private boolean sunk = false;
    
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
        if (sunk) {
            return;
        }

        x += dX;
        y += dY;

        boolean touchingHole = false;
        for (int pixelX = (int) x - RADIUS; pixelX < x + RADIUS && !touchingHole; pixelX++) {
            for (int pixelY = (int) y - RADIUS; pixelY < y + RADIUS && !touchingHole; y++) {
                if (Math.abs(Math.hypot(pixelX - x, pixelY - y) - RADIUS) < 2) {
                    touchingHole = Pool.isHole(pixelX, pixelY);
                }
            }
        }

        if (!touchingHole) {
            if (x - RADIUS < Pool.WALL_SIZE) {
                x = RADIUS + Pool.WALL_SIZE;
                dX *= -.75;
            } if (x + RADIUS >= Pool.WIDTH - Pool.WALL_SIZE) {
                x = Pool.WIDTH - Pool.WALL_SIZE - RADIUS - 1;
                dX *= -.75;
            } if (y - RADIUS < Pool.WALL_SIZE) {
                y = Pool.WALL_SIZE + RADIUS;
                dY *= -.75;
            } if (y + RADIUS >= Pool.HEIGHT - Pool.WALL_SIZE) {
                y = Pool.HEIGHT - Pool.WALL_SIZE - RADIUS - 1;
                dY *= -.75;
            }
        } else {
            if (Pool.isHole((int) x, (int) y)) {
                sink();
            }
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

    private void sink() {
        sunk = true;
    }

    public boolean isSunk() {
        return sunk;
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
        if (sunk) {
            return;
        }
        
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