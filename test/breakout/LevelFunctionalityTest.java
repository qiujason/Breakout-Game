package breakout;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelFunctionalityTest extends DukeApplicationTest {

  private final GameLauncher launcher = new GameLauncher();

  private Scene myScene;

  public void start(Stage stage) {
    // attach scene to the stage and display it
    myScene = launcher.setupScene();
    stage.setScene(myScene);
    stage.show();
  }

  @Test
  public void testLevelOnePositionTopLeftBlock() {
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(10, block.getX());
    assertEquals(28, block.getY());
  }

  @Test
  public void testLevelOneVelocityTopLeftBlock() {
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(0, block.getXVelocity());
    assertEquals(0, block.getYVelocity());
  }

  @Test
  public void testLevelOneStatusTopLeftBlock() {
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(1, block.getLives());
  }

  @Test
  public void testLevelTwoPositionTopLeftBlock() {
    press(myScene, KeyCode.DIGIT2);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(60, block.getX());
    assertEquals(28, block.getY());
  }

  @Test
  public void testLevelTwoVelocityTopLeftBlock() {
    press(myScene, KeyCode.DIGIT2);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(50, block.getXVelocity());
    assertEquals(0, block.getYVelocity());
  }

  @Test
  public void testLevelTwoStatusTopLeftBlock() {
    press(myScene, KeyCode.DIGIT2);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(3, block.getLives());
  }

  @Test
  public void testLevelThreePositionTopLeftBlock() {
    press(myScene, KeyCode.DIGIT3);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(10, block.getX());
    assertEquals(-22, block.getY());
  }

  @Test
  public void testLevelThreeVelocityTopLeftBlock() {
    press(myScene, KeyCode.DIGIT3);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(0, block.getXVelocity());
    assertEquals(-50, block.getYVelocity());
  }

  @Test
  public void testLevelThreeStatusTopLeftBlock() {
    press(myScene, KeyCode.DIGIT3);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(3, block.getLives());
  }

  @Test
  public void testLevelFourPositionTopLeftBlock() {
    press(myScene, KeyCode.DIGIT4);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(10, block.getX());
    assertEquals(28, block.getY());
  }

  @Test
  public void testLevelFourVelocityTopLeftBlock() {
    press(myScene, KeyCode.DIGIT4);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(0, block.getXVelocity());
    assertEquals(0, block.getYVelocity());
  }

  @Test
  public void testLevelFourStatusTopLeftBlock() {
    press(myScene, KeyCode.DIGIT4);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(2, block.getLives());
  }

  @Test
  public void testLevelFivePositionTopLeftBlock() {
    press(myScene, KeyCode.DIGIT5);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(10, block.getX());
    assertEquals(-22, block.getY());
  }

  @Test
  public void testLevelFiveVelocityTopLeftBlock() {
    press(myScene, KeyCode.DIGIT5);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(0, block.getXVelocity());
    assertEquals(-50, block.getYVelocity());
  }

  @Test
  public void testLevelFiveStatusTopLeftBlock() {
    press(myScene, KeyCode.DIGIT5);
    click(myScene, 200, 100);
    javafxRun(() -> launcher.getGame().step(1));
    Block block = lookup("#block00").query();
    assertEquals(3, block.getLives());
  }
}