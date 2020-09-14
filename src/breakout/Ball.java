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

    public Ball(int xPos, int yPos, double xVel, double yVel, int radius, Paint color){
        super(xPos, yPos, radius, color);
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.radius = radius;
        this.color = color;
        setId("ball");
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }
}
