package breakout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class Game extends Application {
    public static final int WINDOWHEIGHT = 600;
    public static final int WINDOWWIDTH = 500;
    public static final Paint BACKGROUND = Color.BEIGE;
    public static final String TITLE = "Breakout"; //TODO: CHANGE LATER
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int RADIUS = 10;
    public static final int NUMGRIDCOLUMNS = 5;
    public static final int NUMGRIDROWS = 5;
    private static final double PADDLEWIDTH = 100;
    private static final double PADDLEHEIGHT = 15;
    public static final int GAP = RADIUS;
    public static final double BLOCKWIDTH = (WINDOWWIDTH - (NUMGRIDCOLUMNS + 1) * GAP) / (double)NUMGRIDCOLUMNS;
    public static final double BLOCKHEIGHT = ((double)WINDOWHEIGHT/2.5 - (NUMGRIDROWS + 1) * GAP) / (double)NUMGRIDROWS;
    private static final double PADDLEDELTA = 20;
    private static final int LIVES = 3;
    private static final int LIVES_DISPLAY_XPOS = 20;
    private static final int LIVES_DISPLAY_YPOS = 15;
    public static final double DISPLAYHEIGHT = 18;
    private static final int SCORE_DISPLAY_XPOS = WINDOWWIDTH - 65;
    private static final int SCORE_DISPLAY_YPOS = 15;

    private Scene myScene;
    private Group root;
    private Paddle paddle;
    private Ball ball;
    private Block[][] gridOfBlocks;
    public ScoreDisplay scoreDisplay;
    private LivesDisplay livesDisplay;

    public boolean pause = false;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        // attach scene to the stage and display it

        myScene = setupScene(WINDOWWIDTH, WINDOWHEIGHT, BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public Scene setupScene(int width, int height, Paint background) throws FileNotFoundException {
        root = new Group();

        ball = new Ball(width / 2, height - RADIUS - (int)PADDLEHEIGHT - 1, RADIUS, Color.ORANGE);
        root.getChildren().add(ball);

        paddle = new Paddle(width/2 - PADDLEWIDTH/2, height - PADDLEHEIGHT, PADDLEWIDTH, PADDLEHEIGHT, PADDLEDELTA, Color.RED); //TODO: Clean this
        root.getChildren().add(paddle);

        BlockConfigurationReader reader = new BlockConfigurationReader();
        gridOfBlocks = reader.loadLevel(root, 1);

        setUpDisplay(root);

        Scene scene = new Scene(root, width, height, background);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void setUpDisplay(Group root) {
        Rectangle display = new Rectangle(WINDOWWIDTH, DISPLAYHEIGHT);
        display.setFill(Color.LIGHTGREY);
        root.getChildren().add(display);

        livesDisplay = new LivesDisplay(LIVES, LIVES_DISPLAY_XPOS, LIVES_DISPLAY_YPOS);
        root.getChildren().add(livesDisplay);

        scoreDisplay = new ScoreDisplay(SCORE_DISPLAY_XPOS, SCORE_DISPLAY_YPOS);
        root.getChildren().add(scoreDisplay);
    }

    void step (double elapsedTime) {
        if (!pause) {
            updateShapes(elapsedTime);
        }
        checkWin();
        checkLoss();
    }

    private void updateShapes (double elapsedTime) {
        checkPaddleCollision();
        checkBorderCollision();
        checkBlockCollision();
        ball.updatePosition(elapsedTime);
    }

    private void checkPaddleCollision() {
        if (isIntersectingWithBall(paddle)) {
            updateVelocityUponCollision(paddle);
        }
    }

    private void checkBlockCollision(){
        for (int i = 0; i < gridOfBlocks.length; i++){
            for (int j = 0; j < gridOfBlocks[0].length; j++){
                Block currentBlock = gridOfBlocks[i][j];
                if (currentBlock.getLives() > 0 && isIntersectingWithBall(currentBlock)) {
                    updateVelocityUponCollision(currentBlock);
                    updateBlockStatus(currentBlock);
                    scoreDisplay.addScore();
                }
            }
        }
    }

    private boolean isIntersectingWithBall(Rectangle gamePiece) {
        return gamePiece.getBoundsInParent().intersects(ball.getBoundsInParent());
    }

    private void updateBlockStatus(Block block) {
        block.subtractLife();
        if (block.getLives() == 0){
            root.getChildren().remove(block);
        }
    }

    private void updateVelocityUponCollision(Rectangle gamePiece) {
        if (intersectBottom(gamePiece) || intersectTop(gamePiece)) {
            ball.reverseYVel();
        } else if (intersectLeft(gamePiece) || intersectRight(gamePiece)) {
            ball.reverseXVel();
        }
    }

    private void checkBorderCollision() {
        if (ball.getLeft() <= 0 || ball.getRight() >= WINDOWWIDTH) {
            ball.reverseXVel();
        } else if (ball.getTop() <= DISPLAYHEIGHT) {
            ball.reverseYVel();
        } else if (ball.getTop() > WINDOWHEIGHT) { // goes below the screen
            reset();
            livesDisplay.subtractLife();
        }
    }

    private void reset() {
        ball.reset();
        paddle.reset();
    }

    private void handleKeyInput (KeyCode code) {
        cheatKeys(code);
        if (pause) {
            return;
        }
        switch(code) {
            case LEFT:
                if (paddle.getX() >= PADDLEDELTA) {
                    paddle.setX(paddle.getX() - PADDLEDELTA);
                    if (!ball.getInMotion()) {
                        ball.setCenterX(ball.getCenterX() - PADDLEDELTA);
                    }
                }
                break;
            case RIGHT:
                if (paddle.getX() + PADDLEWIDTH <= WINDOWWIDTH - PADDLEDELTA) {
                    paddle.setX(paddle.getX() + PADDLEDELTA);
                    if (!ball.getInMotion()) {
                        ball.setCenterX(ball.getCenterX() + PADDLEDELTA);
                    }
                }
                break;
        }
    }

    private void cheatKeys(KeyCode code) {
        if (code == KeyCode.R) {
            paddle.reset();
            ball.reset();
        } else if (code == KeyCode.SPACE) {
            pause = !pause;
        } else if (code == KeyCode.L) {
            livesDisplay.addLife();
        } else if (code == KeyCode.P) {

        } else if (code == KeyCode.C) {
            for (int i = 0; i < gridOfBlocks.length; i++) {
                for (int j = 0; j < gridOfBlocks[0].length; j++) {
                    gridOfBlocks[i][j].setLives(0);
                    root.getChildren().remove(gridOfBlocks[i][j]);
                }
            }
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if (ball.getXVel() == 0 && ball.getYVel() == 0) {
            ball.setXVel(x - WINDOWWIDTH/2);
            ball.setYVel(-250);
        }
    }

    private boolean intersectBottom(Rectangle b){
        return ball.getCenterY() + RADIUS >= b.getY() - b.getArcHeight();
    }

    private boolean intersectTop (Rectangle b){
        return ball.getCenterY() - RADIUS <= b.getY();
    }

    private boolean intersectLeft(Rectangle b){
        return ball.getCenterX() + RADIUS >= b.getX();
    }

    private boolean intersectRight(Rectangle b){
        return ball.getCenterX() - RADIUS <= b.getY() + b.getArcWidth();
    }

    private boolean hasWon(){
        for(Block[] row : gridOfBlocks){
            for (Block b : row){
                if (b.getLives() != 0){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasLost(){
        return livesDisplay.getLives() == 0;
    }

    public void checkWin(){
        if (hasWon()){
            pause = true;
            reset();
            Text winMessage = new Text(200,300, "You Passed This Level!");
            root.getChildren().add(winMessage);
            winMessage.setId("winMessage");
        }
    }

    public void checkLoss(){
        if (hasLost()){
            pause = true;
            reset();
            Text lossMessage = new Text(200,300, "You Ran Out Of Lives! You lost!");
            root.getChildren().add(lossMessage);
            lossMessage.setId("lossMessage");
        }
    }


    public static void main (String[] args) {
        launch(args);
    }
}
