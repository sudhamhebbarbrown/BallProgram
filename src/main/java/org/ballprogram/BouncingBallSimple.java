package org.ballprogram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncingBallSimple extends JPanel {
    private static final int BOX_WIDTH = 800;
    private static final int BOX_HEIGHT = 400;
    private static final int UPDATE_RATE = 30;
    private final List<Ball> balls = new ArrayList<>();
    private static final float SPEED = 3;

    public BouncingBallSimple() {
        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addOrChangeBallAt(e.getX(), e.getY());
            }
        });
        balls.add(new Ball(120, 50, 20, SPEED, SPEED, Color.BLUE));
        balls.add(new Ball(250, 100, 20, SPEED, SPEED, Color.RED));
        balls.add(new Ball(400, 200, 20, SPEED, SPEED, Color.GREEN));


        Thread gameThread = new Thread(() -> {
            while (true) {
                for (Ball ball : balls) {
                    ball.update(BOX_WIDTH, BOX_HEIGHT);
                }
                balls.removeIf(ball -> ball.shouldBeRemoved);
                checkCollisions();
                repaint();
                try {
                    Thread.sleep(1000 / UPDATE_RATE);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        gameThread.start();
    }

    private void addOrChangeBallAt(int x, int y) {
        boolean ballFound = false;

        for (Ball ball : balls) {
            float dx = x - ball.x;
            float dy = y - ball.y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < ball.radius) {
                ballFound = true;
                ball.shouldBeRemoved = true;
                break;
            }
        }

        if (!ballFound) {
            addBallAt(x, y);
        }
    }

    private void addBallAt(int x, int y) {
        Random rand = new Random();
        Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        balls.add(new Ball(x, y, 20, SPEED, SPEED, color));
    }

    private void checkCollisions() {
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball ball1 = balls.get(i);
                Ball ball2 = balls.get(j);
                if (ball1.collidesWith(ball2)) {
                    // Simple collision response by swapping speeds
                    float tempSpeedX = ball1.speedX;
                    float tempSpeedY = ball1.speedY;
                    ball1.speedX = ball2.speedX;
                    ball1.speedY = ball2.speedY;
                    ball2.speedX = tempSpeedX;
                    ball2.speedY = tempSpeedY;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball ball : balls) {
            g.setColor(Color.BLACK);
            int borderThickness = 2;
            int xBorder = (int) (ball.x - ball.radius - borderThickness);
            int yBorder = (int) (ball.y - ball.radius - borderThickness);
            int diameterBorder = (int) (2 * (ball.radius + borderThickness));
            g.fillOval(xBorder, yBorder, diameterBorder, diameterBorder);

            // Draw the ball
            g.setColor(ball.color);
            g.fillOval((int) (ball.x - ball.radius), (int) (ball.y - ball.radius),
                    (int) (2 * ball.radius), (int) (2 * ball.radius));
        }
    }
}

