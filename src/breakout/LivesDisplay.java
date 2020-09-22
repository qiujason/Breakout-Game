package breakout;

import javafx.scene.text.Text;

public class LivesDisplay extends Text {
    int lives;

    public LivesDisplay(int lives, int x, int y) {
        super("Lives: " + lives);
        this.lives = lives;
        setX(x);
        setY(y);
    }

    public int getLives(){
        return lives;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public void subtractLife(){
        setLives(lives - 1);
        updateDisplay();
    }

    public void addLife() {
        setLives(lives + 1);
        updateDisplay();
    }

    private void updateDisplay() {
        setText("Lives: " + lives);
    }
}
