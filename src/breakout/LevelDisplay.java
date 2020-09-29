package breakout;

public class LevelDisplay extends NumericDisplay{
    private int level;

    public LevelDisplay() {
        super("Level", GameStatus.FIRST_LEVEL, GameStatus.LEVEL_DISPLAY_XPOS, GameStatus.DISPLAY_YPOS);
        this.level = 1;
    }

    public void incrementLevel() {
        level += 1;
        updateDisplay();
    }

    public void updateDisplay() {
        setText("Level: " + level);
    }

    public void setLevel(int level){
        this.level = level;
        updateDisplay();
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void resetDisplayValue() {
        level = 0;
    }
}
