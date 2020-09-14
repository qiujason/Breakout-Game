package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    public Paddle(double x, double y, double width, double height, Paint color) {
        super(x, y, width, height);
        setFill(color);
        setId("paddle");
    }
}
