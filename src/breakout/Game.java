package breakout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    public static final int WINDOWHEIGHT = 600;
    public static final int WINDOWWIDTH = 500;
    public static final Paint BACKGROUND = Color.BEIGE;
    public static final String TITLE = "Breakout"; //TODO: CHANGE LATER
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int RADIUS = 10;
    private static final int NUMGRIDCOLUMNS = 5;
    private static final int NUMGRIDROWS = 5;
    private static final double PADDLEWIDTH = 100;
    private static final double PADDLEHEIGHT = 10;
    private static final int GAP = RADIUS * 2;
    private static final double BLOCKWIDTH = (WINDOWWIDTH - (NUMGRIDCOLUMNS + 1) * GAP) / (double)NUMGRIDCOLUMNS;
    private static final double BLOCKHEIGHT = ((double)WINDOWHEIGHT/2.5 - (NUMGRIDROWS + 1) * GAP) / (double)NUMGRIDROWS;

    private Scene myScene;
    private Paddle paddle;
    private Ball ball;

    @Override
    public void start(Stage primaryStage) {
        // attach scene to the stage and display it
//        Group root = new Group();
//        myScene = new Scene(root, SIZE, SIZE, BACKGROUND);

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

    public Scene setupScene(int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();

        // make some shapes and set their properties
        ball = new Ball(width / 2, height - RADIUS - (int)PADDLEHEIGHT, RADIUS, Color.ORANGE);
        root.getChildren().add(ball);

        paddle = new Paddle(width/2 - PADDLEWIDTH/2, height - PADDLEHEIGHT, PADDLEWIDTH, PADDLEHEIGHT, Color.RED); //TODO: Clean this
        root.getChildren().add(paddle);

        Block[][] gridOfBlocks = new Block[NUMGRIDROWS][NUMGRIDCOLUMNS];
//        SingleHitBlock singleHitBlock = new SingleHitBlock(BLOCKWIDTH + 10, 20, BLOCKWIDTH, BLOCKHEIGHT, Color.BLUE);
        for (int row = 0; row < NUMGRIDROWS; row++) {
            for (int column = 0; column < NUMGRIDCOLUMNS; column++) {
                double xPos = column * (BLOCKWIDTH + GAP) + GAP;
                double yPos = row * (BLOCKHEIGHT + GAP) + GAP;
                root.getChildren().add(new SingleHitBlock(xPos, yPos, BLOCKWIDTH, BLOCKHEIGHT, Color.LIGHTGREY));
            }
        }
//        myRacer.setFill(RACER_COLOR);
//        myRacer.setId("racer");
        // x and y represent the top left corner, so center it in window
//        myMover = new Rectangle(width / 2 - MOVER_SIZE / 2, height / 2 - VERTICAL_OFFSET, MOVER_SIZE, MOVER_SIZE);
//        myMover.setArcWidth(MOVER_ROUNDING);
//        myMover.setArcHeight(MOVER_ROUNDING);
//        myMover.setFill(MOVER_COLOR);
//        myMover.setId("mover");
//        myGrower = new Rectangle(width / 2 - GROWER_SIZE / 2, height / 2 + VERTICAL_OFFSET, GROWER_SIZE, GROWER_SIZE);
//        myGrower.setFill(GROWER_COLOR);
//        myGrower.setId("grower");
        // order added to the group is the order in which they are drawn (so last one is on top)
//        root.getChildren().add(ball);
//        root.getChildren().add(paddle);
////        root.getChildren().add(singleHitBlock);
//        for (Block[] blockRow : gridOfBlocks) {
//            for (Block block : blockRow) {
//                root.getChildren().add(block);
//            }
//        }
//        root.getChildren().add(myGrower);
//        root.getChildren().add(myRacer);
        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    // Handle the game's "rules" for every "moment":
    // - movement, how do things change over time
    // - collisions, did things intersect and, if so, what should happen
    // - goals, did the game or level end?
    void step (double elapsedTime) {
        // update "actors" attributes
        updateShapes(elapsedTime);
        // check for collisions (order may matter! and should be its own method if lots of kinds of collisions)
    }

    // Change properties of shapes in small ways to animate them over time
    private void updateShapes (double elapsedTime) {
        // there are more sophisticated ways to animate shapes, but these simple ways work fine to start
        if (ball.getCenterX() - RADIUS <= 0 || ball.getCenterX() + RADIUS >= WINDOWWIDTH) { //TODO: make collision methods
            ball.setXVel(-1 * ball.getXVel());
        } else if (ball.getCenterY() - RADIUS <= 0) {
            ball.setYVel(-1 * ball.getYVel());
        } else { // goes below the screen
//            ball.reset();
        }
        ball.setCenterX(ball.getCenterX() + ball.getXVel() * elapsedTime);
        ball.setCenterY(ball.getCenterY() + ball.getYVel() * elapsedTime);
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.LEFT) {
            if (paddle.getX() >= 0) {
              paddle.setX(paddle.getX() - 20);
            }
        }
        else if (code == KeyCode.RIGHT) {
            if (paddle.getX() + PADDLEWIDTH <= WINDOWWIDTH ) {
              paddle.setX(paddle.getX() + 20);
            }
        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if (ball.getXVel() == 0 && ball.getYVel() == 0) {
            ball.setXVel(-x);
            ball.setYVel(-y);
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
