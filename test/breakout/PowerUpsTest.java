package breakout;

import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DukeApplicationTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PowerUpsTest extends DukeApplicationTest {
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
    public void testExpandPaddlePowerUpActivate() {
        double startWidth = myPaddle.getWidth();
        ExpandPaddlePowerUp powerUp = new ExpandPaddlePowerUp(100, 100, 100, 100);
        powerUp.updateGameStatus(launcher.getGame());
        assertEquals(startWidth * 1.5, myPaddle.getWidth());
    }

    @Test
    public void testExpandPaddlePowerUpReset() {
        double startWidth = myPaddle.getWidth();
        ExpandPaddlePowerUp powerUp = new ExpandPaddlePowerUp(100, 100, 100, 100);
        powerUp.updateGameStatus(launcher.getGame());
        powerUp.resetGameStatus(launcher.getGame());
        assertEquals(startWidth, myPaddle.getWidth());
    }

    @Test
    public void testAddLifePowerUpActivate() {
        int startLives = launcher.getLivesDisplay().getLives();
        AddLifePowerUp powerUp = new AddLifePowerUp(100, 100, 100, 100);
        powerUp.updateGameStatus(launcher.getGame());
        assertEquals(startLives + 1, launcher.getLivesDisplay().getLives());
    }

    @Test
    public void testSlowDownPowerUpActivate() {
        double startXVelocity = 10;
        double startYVelocity = -10;
        myBall.setXVelocity(startXVelocity);
        myBall.setYVelocity(startYVelocity);
        SlowDownPowerUp powerUp = new SlowDownPowerUp(100, 100, 100, 100);
        powerUp.updateGameStatus(launcher.getGame());
        assertEquals(startXVelocity * 0.5, myBall.getXVelocity());
        assertEquals(startYVelocity * 0.5, myBall.getYVelocity());
    }

    @Test
    public void testSlowDownPowerUpReset() {
        double startXVelocity = 10;
        double startYVelocity = -10;
        myBall.setXVelocity(startXVelocity);
        myBall.setYVelocity(startYVelocity);
        SlowDownPowerUp powerUp = new SlowDownPowerUp(100, 100, 100, 100);
        powerUp.updateGameStatus(launcher.getGame());
        powerUp.resetGameStatus(launcher.getGame());
        assertEquals(startXVelocity, myBall.getXVelocity());
        assertEquals(startYVelocity, myBall.getYVelocity());
    }
}