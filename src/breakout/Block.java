package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.*;

abstract class Block extends Rectangle {

    public Block(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public abstract void setNumberOfHits();

    public abstract void setPosition();

    public abstract void setSize();

}
