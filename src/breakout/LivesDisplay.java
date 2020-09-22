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

    public int getLives() {
        return lives;
    }
}
