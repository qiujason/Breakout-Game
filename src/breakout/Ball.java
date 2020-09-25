package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

    private final int originalX;
    private final int originalY;
    private double xPosition;
    private double yPosition;
    private Paint color;
    private int radius;
    private double xVelocity;
    private double yVelocity;

    public Ball(int xPosition, int yPosition, int radius, Paint color){
        super(xPosition, yPosition, radius, color);
        this.originalX = xPosition;
        this.originalY = yPosition;
        this.radius = radius;
        this.color = color;
        this.xVelocity = 0;
        this.yVelocity = 0;
        setId("ball");
    }

    public void reset() {
        setCenterX(originalX);
        setCenterY(originalY);
        xVelocity = 0;
        yVelocity = 0;
    }

    public void setXVel(double value) {
        xVelocity = value;
    }

    public void setYVel(double value) {
        yVelocity = value;
    }

    public void reverseXVel() {
        setXVel(-1 * xVelocity);
    }

    public void reverseYVel() {
        setYVel(-1 * yVelocity);
    }

    public void updatePosition(double elapsedTime) {
        setCenterX(getCenterX() + xVelocity * elapsedTime);
        setCenterY(getCenterY() + yVelocity * elapsedTime);
    }

    public double getXVel() {
        return xVelocity;
    }

    public double getYVel() {
        return yVelocity;
    }

    public boolean getInMotion() {
        return xVelocity != 0 && yVelocity != 0;
    }

    public double getOriginalX() {
        return originalX;
    }

    public double getOriginalY() {
        return originalY;
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

    public double getBottom() {
        return yPosition + radius;
    }

}
