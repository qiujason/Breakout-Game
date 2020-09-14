package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

abstract class Block extends Rectangle {

    public Block(double x, double y, double width, double height, Paint color) {
        super(x, y, width, height);
        setFill(color);
    }

    public abstract void setNumberOfHits();

    public abstract void setPosition();

    public abstract void setSize();
}
