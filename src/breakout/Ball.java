package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

    private int xPos;
    private int yPos;
    private Paint color;
    private int radius;


    public Ball(int xPos, int yPos, int radius, Paint color){
        super(xPos, yPos, radius, color);
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.color = color;
    }

}
