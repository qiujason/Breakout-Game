package breakout;

public class ScoreDisplay extends NumericDisplay {
    private int score;
    private int checkPointScore;
    private int bonus;
    private final int POINT_VALUE_PER_HIT = 10;
    private final int CONSECUTIVE_HIT_BONUS = 5;

    public ScoreDisplay() {
        super("Score", GameStatus.START_SCORE,GameStatus.SCORE_DISPLAY_XPOS,GameStatus.DISPLAY_YPOS);
        this.score = 0;
        this.bonus = 0;
        this.checkPointScore = 0;
    }

    @Override
    public void changeDisplayValue() {
        score += POINT_VALUE_PER_HIT + bonus;
        bonus += CONSECUTIVE_HIT_BONUS;
        updateDisplay();
    }

    @Override
    public void updateDisplay() {
        setText("Score: " + score);
    }

    @Override
    public void resetDisplayValue() {
        score = checkPointScore;
        resetBonus();
        updateDisplay();
    }

    public void resetBonus() {
        bonus = 0;
    }

    public void setCheckPointScore(){
        checkPointScore = score;
    }
}
