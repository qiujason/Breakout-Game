package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends DukeApplicationTest {
    private final Game myGame = new Game();
    private final BlockConfigurationReader reader = new BlockConfigurationReader();

    private Ball myBall;
    private Paddle myPaddle;
    private Scene myScene;

    public void start(Stage stage) throws Exception {
        // attach scene to the stage and display it
        myScene = myGame.setupScene(Game.WINDOWWIDTH, Game.WINDOWHEIGHT, Game.BACKGROUND);
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
        assertEquals(0, myBall.getXVel());
        assertEquals(0, myBall.getYVel());
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
    public void testPaddleMove(){
        double originalPosition = myPaddle.getOrigX();
        press(myScene, KeyCode.RIGHT);
        assertTrue(myPaddle.getX() > originalPosition);
    }

    @Test
    public void testKeyBlocks() throws FileNotFoundException {
        Group root = new Group();
        Block[][] gridOfBlocks = reader.loadLevel(root, 1);
        assertEquals(20, gridOfBlocks[0][0].getX());
        assertEquals(20, gridOfBlocks[0][0].getY());
        assertEquals(20, gridOfBlocks[1][0].getX());
        assertEquals(64, gridOfBlocks[1][0].getY());
    }

    @Test
    public void testCornerHit(){
        myBall.setCenterX(50);
        myBall.setCenterY(60);
        myBall.setXVel(-25);
        myBall.setYVel(-30);

        for(int i=0; i < 4; i++){
            myGame.step(1);
        }

        assertEquals(myBall.getXVel(), 25);
        assertEquals(myBall.getYVel(), 30);
    }

    @Test
    public void testPaddleHit(){
        myBall.setCenterX(250);
        myBall.setCenterY(300);
        myBall.setXVel(0);
        myBall.setYVel(-100);

        for(int i=0; i < 4; i++){
            myGame.step(1);
        }

        assertEquals(myBall.getXVel(), 0);
        assertEquals(myBall.getYVel(), 100);
    }

    @Test
    public void testBottomHit(){
        myBall.setCenterX(100);
        myBall.setCenterY(300);
        myBall.setXVel(0);
        myBall.setYVel(30);

        for(int i=0; i < 12; i++){
            myGame.step(1);
        }

        assertEquals(myBall.getOrigX(), myBall.getCenterX());
        assertEquals(myBall.getOrigY(), myBall.getCenterY());
    }
}