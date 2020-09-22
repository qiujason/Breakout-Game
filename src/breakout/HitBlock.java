package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class HitBlock extends Block {

    private Paint ONE_HIT_BLOCK_COLOR = Color.BLUE;
    private Paint TWO_HIT_BLOCK_COLOR = Color.RED;
    private Paint THREE_HIT_BLOCK_COLOR = Color.YELLOW;

    private int lives;

    public HitBlock(double x, double y, double width, double height, int lives) {
        super(x, y, width, height);
        this.lives = lives;
        setFill(determineColor(lives));
    }

    private Paint determineColor(int lives) {
        if (lives == 1) return ONE_HIT_BLOCK_COLOR;
        if (lives == 2) return TWO_HIT_BLOCK_COLOR;
        return THREE_HIT_BLOCK_COLOR;
    }

    @Override
    public void setNumberOfHits() {

    }

    @Override
    public void setPosition() {

    }

    @Override
    public void setSize() {

    }
}
