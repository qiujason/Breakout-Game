package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    double origX;
    double origY;

    public Paddle(double x, double y, double width, double height, Paint color) {
        super(x, y, width, height);
        origX = x;
        origY = y;
        setFill(color);
        setId("paddle");
        setArcHeight(10);
        setArcWidth(10);
    }

    public void reset() {
        setX(origX);
        setY(origY);
    }
}
