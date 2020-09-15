package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    private final double origX;
    private final double origY;
    private final double delta;

    public Paddle(double x, double y, double width, double height, double delta, Paint color) {
        super(x, y, width, height);
        this.delta = delta;
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

    public double getOrigX() {
        return origX;
    }

    public double getOrigY() {
        return origY;
    }
}
