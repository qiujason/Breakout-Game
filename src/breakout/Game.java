package breakout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    public static final int WINDOWHEIGHT = 600;
    public static final int WINDOWWIDTH = 500;
    public static final Paint BACKGROUND = Color.BLACK;
    public static final String TITLE = "Breakout"; //TODO: CHANGE LATER
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int RADIUS = 10;
    private static final int NUMGRIDCOLUMNS = 5;
    private static final int NUMGRIDROWS = 5;
    private static final double PADDLEWIDTH = 100;
    private static final double PADDLEHEIGHT = 20;
    private static final int GAP = RADIUS * 2;
    private static final double BLOCKWIDTH = (WINDOWWIDTH - (NUMGRIDCOLUMNS + 1) * GAP) / (double)NUMGRIDCOLUMNS;
    private static final double BLOCKHEIGHT = ((double)WINDOWHEIGHT/2.5 - (NUMGRIDROWS + 1) * GAP) / (double)NUMGRIDROWS;

    private Scene myScene;

    @Override
    public void start(Stage primaryStage) {
        // attach scene to the stage and display it
        Group root = new Group();
//        myScene = new Scene(root, SIZE, SIZE, BACKGROUND);

        myScene = setupScene(WINDOWWIDTH, WINDOWHEIGHT, BACKGROUND);
        primaryStage.setScene(myScene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
//        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
//        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public Scene setupScene(int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();

        // make some shapes and set their properties
        Ball ball = new Ball(width / 2, height - RADIUS - (int)PADDLEHEIGHT, 0, 0, RADIUS, Color.WHITE);
        root.getChildren().add(ball);

        Paddle paddle = new Paddle(width/2 - PADDLEWIDTH/2, height - PADDLEHEIGHT, PADDLEWIDTH, PADDLEHEIGHT, Color.RED); //TODO: Clean this
        root.getChildren().add(paddle);

        Block[][] gridOfBlocks = new Block[NUMGRIDROWS][NUMGRIDCOLUMNS];
//        SingleHitBlock singleHitBlock = new SingleHitBlock(BLOCKWIDTH + 10, 20, BLOCKWIDTH, BLOCKHEIGHT, Color.BLUE);
        for (int row = 0; row < NUMGRIDROWS; row++) {
            for (int column = 0; column < NUMGRIDCOLUMNS; column++) {
                double xPos = column * (BLOCKWIDTH + GAP) + GAP;
                double yPos = row * (BLOCKHEIGHT + GAP) + GAP;
                root.getChildren().add(new SingleHitBlock(xPos, yPos, BLOCKWIDTH, BLOCKHEIGHT, Color.BLUE));
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
//        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
