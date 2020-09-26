package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

    private final double startX;
    private final double startY;
    private final double delta;


    public Paddle(double x, double y, double width, double height, double delta, Paint color) {
        super(x, y, width, height);
        this.delta = delta;
        startX = x;
        startY = y;
        setFill(color);
        setId("paddle");
        setArcHeight(10);
        setArcWidth(10);
    }

    public void reset() {
        setX(startX);
        setY(startY);
    }

    public double getStartX(){
        return this.startX;
    }

    public double getStartY(){
        return this.startY;
    }
}
