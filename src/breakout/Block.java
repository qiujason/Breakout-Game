package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle implements GamePiece {

    //TODO: make into better data structure
    private final Paint ONE_HIT_BLOCK_COLOR = Color.web("#bae1ff");
    private final Paint TWO_HIT_BLOCK_COLOR = Color.web("#ffb3ba");
    private final Paint THREE_HIT_BLOCK_COLOR = Color.web("#baffc9");

    private int lives;

    public Block(double x, double y, int lives) {
        super(x, y, GamePiece.WIDTH, GamePiece.HEIGHT);
        this.lives = lives;
        setFill(determineColor());
    }

    public Paint determineColor() {
        if (lives == 1) {
            return ONE_HIT_BLOCK_COLOR;
        }
        if (lives == 2) {
            return TWO_HIT_BLOCK_COLOR;
        }
        return THREE_HIT_BLOCK_COLOR;
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public void updateStatus() {
        this.lives -= 1;
        setFill(determineColor());
    }
}
