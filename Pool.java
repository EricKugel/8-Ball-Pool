import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Pool extends JFrame {
    private Ball cueBall;
    private Ball test;
    private Stick stick;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

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
        cueBall = new Ball(Color.WHITE);
        test = new Ball();
        stick = new Stick(WIDTH / 2, HEIGHT / 2, 0);
        main = new JPanel() {
            public void paintComponent(Graphics g) {
                draw(g);
            }
        };
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
        cueBall.update();
        test.update();

        if (cueBall.isTouching(test)) {
            cueBall.collideWith(test);
        }

        if (!fireButton.isEnabled() && !cueBall.isMoving() && !test.isMoving()) {
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
        g.setColor(new Color(18, 140, 39));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Make sure drawing the stick comes last because it has to rotate the graphics
        cueBall.draw(g);
        test.draw(g);
        stick.draw(g);
    }

    private void fire() {
        double speed = Stick.FULL_SPEED * (slider.getValue() / 100.0);
        cueBall.setVector(Vector.createVectorFromTrig(stick.getDirection(), speed));
        fireButton.setEnabled(false);
        stick.setDistanceFromBall(0);
        slider.setValue(0);
    }

    public static void main(String[] arg0) {
        new Pool();
    }
}