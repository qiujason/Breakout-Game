package breakout;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import util.BreakoutApplicationTest;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends BreakoutApplicationTest {
    private final Game myGame = new Game();
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
        FxRobot robot = new FxRobot();

        double originalPosition = myPaddle.getOrigX();
        System.out.println(myBall.getOrigX());
        robot.clickOn();
        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        click(myScene, 200, 300);
        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests
        System.out.println(myBall.getOrigX());
        press(myScene, KeyCode.RIGHT);
        sleep(1, TimeUnit.SECONDS);    // PAUSE: but useful when debugging to verify what is happening
        System.out.println(myPaddle.getX());

        assertTrue(myPaddle.getX() > originalPosition);
    }











    @Test
    public void testCornerHit(){
        myBall.setCenterX(50);
        myBall.setCenterY(60);
        myBall.setXVel(-25);
        myBall.setYVel(-30);

        myBall.setInMotion(true);
        for(int i=0; i < 4; i++){
            myGame.step(1);
        }

        assertEquals(myBall.getXVel(), 25);
        assertEquals(myBall.getYVel(), 30);
    }
}