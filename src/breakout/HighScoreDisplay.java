package breakout;

public class HighScoreDisplay extends NumericDisplay{

    private int highScore;
    private HighScoreReader scoreReader;

    public HighScoreDisplay(int highScore){
        super("High Score", highScore, GameStatus.HIGH_SCORE_DISPLAY_XPOS, GameStatus.DISPLAY_YPOS);
        this.highScore = highScore;
        scoreReader = new HighScoreReader();
    }

    public void updateHighScore(int currentScore) {
        if (currentScore >= highScore){
            highScore = currentScore;
            updateDisplay();

        }
    }

    @Override
    public void updateDisplay() {
        setText("High Score: " + highScore);
    }

    @Override
    public void resetDisplayValue() {
        highScore = scoreReader.readInHighScore();
    }

    public void clearHighScore() {
        highScore = 0;
        scoreReader.replaceHighScore(highScore);
        updateDisplay();
    }
}
