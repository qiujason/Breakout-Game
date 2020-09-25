package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

    private final int startX;
    private final int startY;
    private Paint color;
    private int radius;
    private double xVelocity;
    private double yVelocity;

    public Ball(int xPosition, int yPosition, int radius, Paint color){
        super(xPosition, yPosition, radius, color);
        this.startX = xPosition;
        this.startY = yPosition;
        this.radius = radius;
        this.color = color;
        this.xVelocity = 0;
        this.yVelocity = 0;
        setId("ball");
    }

    public void reset() {
        setCenterX(startX);
        setCenterY(startY);
        xVelocity = 0;
        yVelocity = 0;
    }

    public void setXVelocity(double value) {
        xVelocity = value;
    }

    public void setYVelocity(double value) {
        yVelocity = value;
    }

    public void reverseXVelocity() {
        setXVelocity(-1 * xVelocity);
    }

    public void reverseYVelocity() {
        setYVelocity(-1 * yVelocity);
    }

    public void updatePosition(double elapsedTime) {
        setCenterX(getCenterX() + xVelocity * elapsedTime);
        setCenterY(getCenterY() + yVelocity * elapsedTime);
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public boolean getInMotion() {
        return xVelocity != 0 && yVelocity != 0;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getLeft() {
        return getCenterX() - radius;
    }

    public double getRight() {
        return getCenterX() + radius;
    }

    public double getTop() {
        return getCenterY() - radius;
    }
}
