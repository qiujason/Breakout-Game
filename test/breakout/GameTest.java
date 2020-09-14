package breakout;

import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends ApplicationTest {
    private final Game myGame = new Game();
    private Ball myBall;
    private Paddle myPaddle;
    private Scene myScene;


    public void start(Stage stage) throws Exception {
        // attach scene to the stage and display it
        myScene = myGame.setupScene(Game.SIZE, Game.SIZE, Game.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        myBall = lookup("#ball").query();
        myPaddle = lookup("#paddle").query();
    }

    @Test
    public void testInitialPositions(){
        assertEquals(250, myBall.getCenterX());
        assertEquals(570, myBall.getCenterY());
        assertEquals(10, myBall.getRadius());
        assertEquals(2500, myPaddle.getX());
        assertEquals(580, myPaddle.getY());
        assertEquals(100, myPaddle.getWidth());
        assertEquals(20, myPaddle.getHeight());
    }
}