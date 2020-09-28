package breakout;

public class LivesDisplay extends NumericDisplay {

    int lives;

    public LivesDisplay() {
        super("Lives", GameStatus.STARTING_LIVES, GameStatus.LIVES_DISPLAY_XPOS, GameStatus.DISPLAY_YPOS);
        this.lives = 3;
    }

    public int getLives(){
        return lives;
    }


    public void addLife() {
        lives += 1;
        updateDisplay();
    }

    public void subtractLife(){
        lives -= 1;
        updateDisplay();
    }

    public void updateDisplay() {
        setText("Lives: " + lives);
    }

    @Override
    public void resetDisplayValue() {
        lives = 3;
        updateDisplay();
    }
}
