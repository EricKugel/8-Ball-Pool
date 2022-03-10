import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Pool extends JFrame {
    private Ball ball;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

    public Pool() {
        setTitle("Pool");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI();
        setVisible(true);
        pack();
    }

    private void initGUI() {
        ball = new Ball();
        JPanel main = new JPanel() {
            public void paintComponent(Graphics g) {
                draw(g);
            }
        };
        main.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setContentPane(main);
    
        Timer timer = new Timer(1000 / 24, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ball.update();
                repaint();
            }
        });

        timer.start();
    }

    private void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        ball.draw(g);
    }

    public static void main(String[] arg0) {
        new Pool();
    }
}