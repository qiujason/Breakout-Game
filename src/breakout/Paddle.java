package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

    private final double startX;
    private final double startY;

    public Paddle(double x, double y, double width, double height, Paint color) {
        super(x, y, width, height);
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

//    public void changeWidth(double newWidth) {
//        setWidth(newWidth);
//    }
//
//    public void changeHeight(double newHeight) {
//        setHeight(newWidth);
//    }
}
