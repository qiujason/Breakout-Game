package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

    private final int origX;
    private final int origY;
    private int xPos;
    private int yPos;
    private Paint color;
    private int radius;
    private double xVel = 0;
    private double yVel = 0;
    private boolean inMotion = false;

    public Ball(int xPos, int yPos, int radius, Paint color){
        super(xPos, yPos, radius, color);
        this.xPos = xPos;
        this.yPos = yPos;
        this.origX = xPos;
        this.origY = yPos;
        this.radius = radius;
        this.color = color;
        setId("ball");
    }

    public void reset() {
        xPos = origX;
        yPos = origY;
        setCenterX(xPos);
        setCenterY(yPos);
        xVel = 0;
        yVel = 0;
    }

    public void setXVel(double value) {
        xVel = value;
    }

    public void setYVel(double value) {
        yVel = value;
    }

    public void setInMotion(boolean inMotion) {
        this.inMotion = inMotion;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public boolean getInMotion() {
        return inMotion;
    }

    public double getOrigX() {
        return origX;
    }

    public double getOrigY() {
        return origY;
    }

//    public void updatePosition() {
//        double distance = Math.sqrt(xPos * xPos + yPos * yPos);
//        xPos += xVel / distance;
//        yPos += yVel / distance;
//    }
}
