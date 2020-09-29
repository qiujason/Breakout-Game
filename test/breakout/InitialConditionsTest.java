package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DukeApplicationTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InitialConditionsTest extends DukeApplicationTest {
    private final GameLauncher launcher = new GameLauncher();
    private final BlockConfigurationReader reader = new BlockConfigurationReader();

    private Ball myBall;
    private Paddle myPaddle;

    public void start(Stage stage) {
        // attach scene to the stage and display it
        Scene myScene = launcher.setupScene();
        stage.setScene(myScene);
        stage.show();

        myBall = lookup("#ball").query();
        myPaddle = lookup("#paddle").query();
    }

    @Test
    public void testInitialBallPosition(){
        assertEquals(250, myBall.getCenterX());
        assertEquals(574, myBall.getCenterY());
    }

    @Test
    public void testInitialBallSize(){
        assertEquals(10, myBall.getRadius());
    }

    @Test
    public void testInitialBallVelocity(){
        assertEquals(0, myBall.getXVelocity());
        assertEquals(0, myBall.getYVelocity());
    }

    @Test
    public void testInitialPaddlePosition(){
        assertEquals(200, myPaddle.getX());
        assertEquals(585, myPaddle.getY());
    }

    @Test
    public void testInitialPaddleSize(){
        assertEquals(100, myPaddle.getWidth());
        assertEquals(15, myPaddle.getHeight());
    }

    @Test
    public void testKeyBlocks() {
        Group root = new Group();
        GamePiece[][] gridOfGamePieces = reader.loadLevel(root, 1);
        assertEquals(10, gridOfGamePieces[0][0].getX());
        assertEquals(28, gridOfGamePieces[0][0].getY());
        assertEquals(10, gridOfGamePieces[1][0].getX());
        assertEquals(74, gridOfGamePieces[1][0].getY());
    }
}