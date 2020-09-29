package breakout;

import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DukeApplicationTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MethodTest extends DukeApplicationTest {
    private final GameLauncher launcher = new GameLauncher();
    private final BlockConfigurationReader reader = new BlockConfigurationReader();

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
    public void testHandleMouseInputLeft() {
        click(myScene, 200, 100);
        javafxRun(() -> launcher.getGame().step(1));
        assertEquals(-50, myBall.getXVelocity());
        assertEquals(-150, myBall.getYVelocity());
    }

    @Test
    public void testHandleMouseInputRight() {
        click(myScene, 400, 100);
        javafxRun(() -> launcher.getGame().step(1));
        assertEquals(150, myBall.getXVelocity());
        assertEquals(-150, myBall.getYVelocity());
    }
}