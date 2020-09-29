package breakout;

import javafx.scene.image.Image;

import java.io.File;

public class AddLifePowerUp extends PowerUp {

  public AddLifePowerUp(double x, double y, double width, double height) {
    super(x, y, width, height, new Image(new File(GameStatus.HEART_FILE_PATH).toURI().toString()));
  }

  @Override
  public void updateGameStatus(Game game) {
    if (!super.isActive()) {
      game.addLife();
      super.setActive();
    }
  }

  @Override
  public void resetGameStatus(Game game) {
  } // not timer dependent
}
