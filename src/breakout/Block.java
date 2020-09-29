package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Block extends GamePiece {

  //TODO: make into better data structure
  private final Paint ONE_HIT_BLOCK_COLOR = Color.web("#bae1ff");
  private final Paint TWO_HIT_BLOCK_COLOR = Color.web("#ffb3ba");
  private final Paint THREE_HIT_BLOCK_COLOR = Color.web("#baffc9");

  public Block(double x, double y, double width, double height, int lives) {
    super(x, y, width, height, lives);
    setFill(determineColor(lives));
  }

  public Paint determineColor(int lives) {
    return switch (lives) {
      case 1 -> ONE_HIT_BLOCK_COLOR;
      case 2 -> TWO_HIT_BLOCK_COLOR;
      case 3 -> THREE_HIT_BLOCK_COLOR;
      default -> THREE_HIT_BLOCK_COLOR;
    };
  }

  public void updateStatus() {
    subtractLife();
    setFill(determineColor(getLives()));
  }
}
