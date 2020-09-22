package breakout;

import javafx.scene.text.Text;

public class ScoreDisplay extends Text {
    private int score;
    private int bonus;
    private final int POINT_VALUE = 100;
    private final int START_BONUS = 10;
    private final double BONUS_MULTIPLIER = 2;

    public ScoreDisplay(int x, int y) {
        super("Score: " + 0);
        setX(x);
        setY(y);
        this.score = 0;
        this.bonus = START_BONUS;
    }

    public void addScore() {
        score += POINT_VALUE + bonus;
        bonus *= BONUS_MULTIPLIER;
        updateDisplay();
    }

    private void updateDisplay() {
        setText("Score: " + score);
    }

    public void resetBonus() {
        bonus = START_BONUS;
    }

    public void resetScore() {
        score = 0;
        resetBonus();
    }

    public int getScore() {
        return score;
    }
}
