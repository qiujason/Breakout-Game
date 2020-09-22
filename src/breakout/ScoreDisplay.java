package breakout;

public class ScoreDisplay extends Display {
    int score = 0;

    public ScoreDisplay(int x, int y) {
        super("Score: " + 0);
        setX(x);
        setY(y);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void resetScore() {
        score = 0;
    }

    public int getScore() {
        return score;
    }
}
