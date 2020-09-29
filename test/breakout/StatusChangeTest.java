package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.DukeApplicationTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatusChangeTest extends DukeApplicationTest {
    private final GameLauncher launcher = new GameLauncher();

    private Ball myBall;
    private Scene myScene;

    public void start(Stage stage) {
        // attach scene to the stage and display it
        myScene = launcher.setupScene();
        stage.setScene(myScene);
        stage.show();

        myBall = lookup("#ball").query();
    }

    @Test
    public void testLoseLife() {
        myBall.setCenterX(100);
        myBall.setCenterY(300);
        myBall.setXVelocity(0);
        myBall.setYVelocity(30);

        for(int i=0; i < 12; i++){
            javafxRun(() -> launcher.getGame().step(1));
        }

        assertEquals(2, launcher.getLivesDisplay().getLives());
    }

    @Test
    public void testBallHitUpdateScore() {
        myBall.setCenterX(50);
        myBall.setCenterY(300);
        myBall.setXVelocity(0);
        myBall.setYVelocity(-250);

        for (int i = 0; i < 12; i++) {
            javafxRun(() -> launcher.getGame().step(GameStatus.SECOND_DELAY));
        }

        assertEquals(10, launcher.getScoreDisplay().getScore());
    }

    @Test
    public void testBallHitDeleteBlock() {
        myBall.setCenterX(50);
        myBall.setCenterY(300);
        myBall.setXVelocity(0);
        myBall.setYVelocity(-250);

        for (int i = 0; i < 12; i++) {
            javafxRun(() -> launcher.getGame().step(GameStatus.SECOND_DELAY));
        }

        Exception e = assertThrows(Exception.class, () -> lookup("#block40").query());
        assertEquals("there is no node in the scene-graph matching the query: NodeQuery: from nodes:", e.getMessage().substring(0, 78));
    }

    @Test
    public void testLevelClear() {
        press(myScene, KeyCode.C);
        javafxRun(() -> launcher.getGame().step(GameStatus.SECOND_DELAY));
        Text winMessage = lookup("#winMessage").query();
        assertTrue(myScene.getRoot().getChildrenUnmodifiable().contains(winMessage));
    }

    @Test
    public void testLevelLost(){
        for(int i=0; i < 30; i++){
            click(myScene, 200, 500);
            press(myScene, KeyCode.RIGHT);
            javafxRun(() -> launcher.getGame().step(1));
        }
        Text lossMessage = lookup("#lossMessage").query();
        assertTrue(myScene.getRoot().getChildrenUnmodifiable().contains(lossMessage));
    }

    @Test
    public void testGameWon(){
        for (int i = 0; i < 5; i++) {
            press(myScene, KeyCode.C);
            javafxRun(() -> launcher.getGame().step(GameStatus.SECOND_DELAY));
        }
        Text winMessage = lookup("#winGameMessage").query();
        assertTrue(myScene.getRoot().getChildrenUnmodifiable().contains(winMessage));
    }

    @Test
    public void testLevelChange() {
        press(myScene, KeyCode.DIGIT2);
        assertEquals(2, launcher.getLevelDisplay().getLevel());
        press(myScene, KeyCode.DIGIT3);
        assertEquals(3, launcher.getLevelDisplay().getLevel());
        press(myScene, KeyCode.DIGIT4);
        assertEquals(4, launcher.getLevelDisplay().getLevel());
        press(myScene, KeyCode.DIGIT5);
        assertEquals(5, launcher.getLevelDisplay().getLevel());
        press(myScene, KeyCode.DIGIT1);
        assertEquals(1, launcher.getLevelDisplay().getLevel());
    }

    @Test
    public void testHighScoreChange() {
        launcher.getHighScoreDisplay().clearHighScore();
        myBall.setCenterX(50);
        myBall.setCenterY(300);
        myBall.setXVelocity(0);
        myBall.setYVelocity(-250);

        for (int i = 0; i < 12; i++) {
            javafxRun(() -> launcher.getGame().step(GameStatus.SECOND_DELAY));
        }

        assertTrue(launcher.getHighScoreDisplay().getHighScore() > 0);
    }

}