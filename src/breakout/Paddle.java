package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

  private final double startX;

  public Paddle(double x, double y, double width, double height, Paint color) {
    super(x, y, width, height);
    startX = x;
    setFill(color);
    setId("paddle");
    setArcHeight(10);
    setArcWidth(10);
  }

  public void reset() {
    setX(startX);
  }

  public double getStartX() {
    return startX;
  }
}
