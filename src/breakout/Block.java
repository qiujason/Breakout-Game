package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {

    private Paint ONE_HIT_BLOCK_COLOR = Color.web("#bae1ff");
    private Paint TWO_HIT_BLOCK_COLOR = Color.web("#ffb3ba");
    private Paint THREE_HIT_BLOCK_COLOR = Color.web("#baffc9");

    private int lives;

    public Block(double x, double y, double width, double height, int lives) {
        super(x, y, width, height);
        this.lives = lives;
        setFill(determineColor());
    }

    public Paint determineColor() {
        if (lives == 1) return ONE_HIT_BLOCK_COLOR;
        if (lives == 2) return TWO_HIT_BLOCK_COLOR;
        return THREE_HIT_BLOCK_COLOR;
    }

    public int getLives(){
        return lives;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public void subtractLife(){
        setLives(getLives() - 1);
        setFill(determineColor());
    }

}
