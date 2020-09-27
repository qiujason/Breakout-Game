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
    public static final int NUMGRIDCOLUMNS = 5;
    public static final int NUMGRIDROWS = 5;
    public static final double PADDLEWIDTH = 100;
    public static final double PADDLEHEIGHT = 15;
    public static final int GAP = RADIUS;
    public static final double BLOCKWIDTH = (WINDOWWIDTH - (NUMGRIDCOLUMNS + 1) * GAP) / (double)NUMGRIDCOLUMNS;
    public static final double BLOCKHEIGHT = ((double)WINDOWHEIGHT/2.5 - (NUMGRIDROWS + 1) * GAP) / (double)NUMGRIDROWS;
    public static final double PADDLEDELTA = 20;
    public static final int LIVES = 3;
    public static final int LIVES_DISPLAY_XPOS = 20;
    public static final int LIVES_DISPLAY_YPOS = 15;
    public static final double DISPLAYHEIGHT = 18;
    public static final int SCORE_DISPLAY_XPOS = WINDOWWIDTH - 65;
    public static final int SCORE_DISPLAY_YPOS = 15;
    public static final int FIRST_LEVEL = 1;
}
