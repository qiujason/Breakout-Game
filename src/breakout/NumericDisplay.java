package breakout;

import javafx.scene.text.Text;

public abstract class NumericDisplay extends Text {

  public NumericDisplay(String label, int display, int xPos, int yPos) {
    super(label + ": " + display);
    setX(xPos);
    setY(yPos);
  }

  public abstract void updateDisplay();

  public abstract void resetDisplayValue();

}
