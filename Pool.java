import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;

public class Pool extends JFrame {
    public static final int WIDTH = 518;
    public static final int HEIGHT = 296;
    public static final int WALL_SIZE = 37;

    private static BufferedImage tableImage = null;

    private Ball cueBall;
    private Ball[] balls = new Ball[11];
    private Stick stick;
    private Hole[] holes = new Hole[6];

    private JPanel main;
    private JPanel sidePanel;
    private JSlider slider;
    private JButton fireButton;

    public Pool() {
        setTitle("Pool");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI();
        setVisible(true);
        pack();
    }

    private void initGUI() {
        cueBall = new Ball();
        balls[0] = cueBall;
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED, Color.BLACK, Color.GRAY, Color.CYAN, Color.LIGHT_GRAY};
        int[] xPoints = {0, 1, 1, 2, 2, 2, 3, 3, 3, 3};
        
        double yStart = 0;
        double y = yStart;
        int x = 0;
        for (int i = 1; i < balls.length; i++) {
            if (x != xPoints[i - 1]) {
                x = xPoints[i - 1];
                yStart -= .5;
                y = yStart;
            }
            Ball ball = new Ball(colors[i - 1], WIDTH / 2 + (x * Ball.RADIUS * 2), HEIGHT / 2 + (y * Ball.RADIUS * 2));
            balls[i] = ball;
            
            y += 1;
        }

        stick = new Stick(cueBall.getX(), cueBall.getY(), 0);
        main = new JPanel() {
            public void paintComponent(Graphics g) {
                draw(g);
            }
        };


        // double holeX[] = {Math.sqrt(2), WIDTH / 2, 1, 0, .5, 1};
        // double holeY[] = {0, 0, 0, 1, 1, 1};
        // for (int i = 0; i < 6; i++) {
        //     x = holeX[i] * WIDTH;
        //     y = holeY[i] * HEIGHT;
        //     if (holeX[i] != 0.5) {
            
        //     }
        //     Hole hole = new Hole(holeX[i] * WIDTH, holeY[i] * HEIGHT);
        //     holes[i] = hole;
        // }

        try {
            tableImage = ImageIO.read(getClass().getResourceAsStream("table.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        main.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(main, BorderLayout.CENTER);

        main.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                double x = e.getX() - stick.getX();
                double y = e.getY() - stick.getY();
                stick.setDirection(Math.atan(y / x) + (x < 0 ? Math.PI : 0));
            }
        });

        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        slider = new JSlider(SwingConstants.VERTICAL, 0, 100, 0);
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        fireButton = new JButton("Fire");
        fireButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        fireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fire();
            }
        });
        sidePanel.add(slider);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidePanel.add(fireButton);
        add(sidePanel, BorderLayout.EAST);
    
        Timer timer = new Timer(1000 / 24, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });

        timer.start();
    }

    private void tick() {
        for (Ball ball : balls) {
            ball.update();
        }

        for (int i = 0; i < balls.length - 1; i++) {
            for (int j = i + 1; j < balls.length; j++) {
                if (balls[i].isTouching(balls[j])) {
                    balls[i].collideWith(balls[j]);
                }
            }
        }
        
        boolean isMoving = cueBall.isMoving();
        if (!isMoving) {
            for (Ball ball : balls) {
                if (ball.isMoving()) {
                    isMoving = true;
                    break;
                }
            }
        }

        if (!fireButton.isEnabled() && !isMoving) {
            stick.setX(cueBall.getX());
            stick.setY(cueBall.getY());
            stick.setDirection(0);
            fireButton.setEnabled(true);
        }

        if (fireButton.isEnabled()) {
            stick.setDistanceFromBall(slider.getValue() / 2);
        }
        repaint();
    }

    private void draw(Graphics g) {
        // g.setColor(new Color(38, 160, 59));
        // g.fillRect(0, 0, WIDTH, HEIGHT);
        // g.setColor(new Color(18, 140, 39));
        // g.fillRect(WALL_SIZE, WALL_SIZE, WIDTH - WALL_SIZE * 2, HEIGHT - WALL_SIZE * 2);
        g.drawImage(tableImage, 0, 0, null);

        // Make sure drawing the stick comes last because it has to rotate the graphics
        cueBall.draw(g);
        for (Ball ball : balls) {
            ball.draw(g);
        }

        // for (Hole hole : holes) {
        //     hole.draw(g);
        // }

        stick.draw(g);
    }

    private void fire() {
        double speed = Stick.FULL_SPEED * (slider.getValue() / 100.0);
        cueBall.setVector(Vector.createVectorFromTrig(stick.getDirection(), speed));
        fireButton.setEnabled(false);
        stick.setDistanceFromBall(0);
        slider.setValue(0);
    }

    public static boolean isHole(int x, int y) {
        return tableImage.getRGB(x, y) == Color.BLACK.getRGB();
    }

    public static void main(String[] arg0) {
        new Pool();
    }
}