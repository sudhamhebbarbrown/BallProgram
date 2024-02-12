package org.ballprogram;

import java.awt.*;

public class Ball {
    float radius;
    float x;
    float y;
    float speedX;
    float speedY;
    Color color;
    boolean shouldBeRemoved = false;

    // Constructor
    public Ball(float x, float y, float radius, float speedX, float speedY, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
    }

    // Move the ball and reflect off bounds
    public void update(int boxWidth, int boxHeight) {
        x += speedX;
        y += speedY;
        if (x - radius < 0 || x + radius > boxWidth) {
            speedX = -speedX;
            x = Math.max(radius, Math.min(x, boxWidth - radius));
        }
        if (y - radius < 0 || y + radius > boxHeight) {
            speedY = -speedY;
            y = Math.max(radius, Math.min(y, boxHeight - radius));
        }
    }

    // Check collision with another ball
    public boolean collidesWith(Ball other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        float distance = (float)Math.sqrt(dx * dx + dy * dy);
        return distance < this.radius + other.radius;
    }
}
