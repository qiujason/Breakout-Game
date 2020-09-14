package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

    private int xPos;
    private int yPos;
    private Paint color;
    private int radius;
    private double xVel = 0;
    private double yVel = 0;

    public Ball(int xPos, int yPos, int radius, Paint color){
        super(xPos, yPos, radius, color);
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.color = color;
        setId("ball");
    }

    public void setXVel(double value) {
        xVel = value;
    }

    public void setYVel(double value) {
        yVel = value;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

//    public void updatePosition() {
//        double distance = Math.sqrt(xPos * xPos + yPos * yPos);
//        xPos += xVel / distance;
//        yPos += yVel / distance;
//    }
}
