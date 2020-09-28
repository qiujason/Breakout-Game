package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public final class GameStatus {
    public static final int WINDOWHEIGHT = 600;
    public static final int WINDOWWIDTH = 500;
    public static final Paint BACKGROUND = Color.web("#f0f8ff");
    public static final String TITLE = "Breakout"; //TODO: CHANGE LATER
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int RADIUS = 10;
    public static final double PADDLEWIDTH = 100;
    public static final double PADDLEHEIGHT = 15;
    public static final int GAP = RADIUS;
    public static final double PADDLEDELTA = 20;
    public static final int LIVES_DISPLAY_XPOS = 10;
    public static final int DISPLAY_YPOS = 14;
    public static final int LEVEL_DISPLAY_XPOS = 125;
    public static final int HIGH_SCORE_DISPLAY_XPOS = 290;
    public static final double DISPLAYHEIGHT = 18;
    public static final int SCORE_DISPLAY_XPOS = WINDOWWIDTH - 65;
    public static final int FIRST_LEVEL = 1;
    public static final int START_SCORE = 0;
    public static final int STARTING_LIVES = 3;
}
