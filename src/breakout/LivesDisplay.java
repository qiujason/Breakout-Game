package breakout;

public class LivesDisplay extends Display {
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
