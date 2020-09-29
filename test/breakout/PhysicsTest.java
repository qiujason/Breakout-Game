package breakout;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import util.DukeApplicationTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhysicsTest extends DukeApplicationTest {
    private final GameLauncher launcher = new GameLauncher();

    private Ball myBall;
    private Paddle myPaddle;
    private Scene myScene;

    public void start(Stage stage) {
        // attach scene to the stage and display it
        myScene = launcher.setupScene();
        stage.setScene(myScene);
        stage.show();

        myBall = lookup("#ball").query();
        myPaddle = lookup("#paddle").query();
    }

    @Test
    public void testPaddleMove(){
        double originalPosition = myPaddle.getStartX();
        press(myScene, KeyCode.RIGHT);
        assertTrue(myPaddle.getX() > originalPosition);
    }

    @Test
    public void testCornerHit(){
        for (int i = 0; i < 3; i++) {
            myBall.setCenterX(50);
            myBall.setCenterY(60);
            myBall.setXVelocity(-25);
            myBall.setYVelocity(-30);

            for (int j = 0; j < 4; j++) {
                javafxRun(() -> launcher.getGame().step(1));
            }
        }

        assertEquals(25, myBall.getXVelocity());
        assertEquals(30, myBall.getYVelocity());
    }

    @Test
    public void testPaddleHit(){
        myBall.setCenterX(250);
        myBall.setCenterY(300);
        myBall.setXVelocity(0);
        myBall.setYVelocity(-100);

        for(int i=0; i < 4; i++){
            javafxRun(() -> launcher.getGame().step(1));
        }

        assertEquals(0, myBall.getXVelocity());
        assertEquals(100, myBall.getYVelocity());
    }

    @Test
    public void testBottomHit() {
        myBall.setCenterX(100);
        myBall.setCenterY(300);
        myBall.setXVelocity(0);
        myBall.setYVelocity(30);

        for(int i=0; i < 12; i++){
            javafxRun(() -> launcher.getGame().step(1));
        }

        assertEquals(myBall.getCenterX(), myBall.getStartX());
        assertEquals(myBall.getCenterY(), myBall.getStartY());
    }

    @Test
    public void testBallHitBounces() {
        myBall.setCenterX(50);
        myBall.setCenterY(300);
        myBall.setXVelocity(0);
        myBall.setYVelocity(-250);

        for (int i = 0; i < 12; i++) {
            javafxRun(() -> launcher.getGame().step(GameStatus.SECOND_DELAY));
        }

        assertEquals(0, myBall.getXVelocity());
        assertEquals(250, myBall.getYVelocity());
    }
}