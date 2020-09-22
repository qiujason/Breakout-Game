package breakout;

import javafx.scene.text.Text;

public class ScoreDisplay extends Text {
    private int score;
    private int bonus;
    private final int POINT_VALUE = 100;

    public ScoreDisplay(int x, int y, ) {
        super("Score: " + 0);
        setX(x);
        setY(y);
        this.score = 0;
        this.bonus = 50;
    }

    public void addScore() {
        score += POINT_VALUE + bonus;
        bonus *= 1.5;
    }

    private void updateDisplay() {
        setText("Score: " + score);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void resetScore() {
        score = 0;
        bonus = 50;
    }

    public int getScore() {
        return score;
    }
}
