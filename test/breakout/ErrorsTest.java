package breakout;

import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DukeApplicationTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorsTest extends DukeApplicationTest {
  private final GameLauncher launcher = new GameLauncher();
  private final BlockConfigurationReader reader = new BlockConfigurationReader();


  public void start(Stage stage) {
    // attach scene to the stage and display it
    Scene myScene = launcher.setupScene();
    stage.setScene(myScene);
    stage.show();
  }

  @Test
  public void testInvalidBlockConfigurationError() {
    Exception e = assertThrows(Exception.class, () ->
        reader.readInBlocks(6, new GamePiece[5][5]));
    assertEquals("Invalid Block Configuration File!", e.getMessage());
  }

  @Test
  public void testInvalidHighScoreReadError() {
    HighScoreReader.setHighScoreFilePath("data/highscore/highscore.txt");
    HighScoreReader highScoreReader = new HighScoreReader();
    Exception e = assertThrows(Exception.class, highScoreReader::readInHighScore);
    assertEquals("Invalid High Score File", e.getMessage());
  }

}