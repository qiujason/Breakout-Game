package breakout;

import javafx.scene.image.Image;

import java.io.File;

public class SlowDownPowerUp extends PowerUp {

    public SlowDownPowerUp(double x, double y, double width, double height) {
        super(x, y, width, height, new Image(new File(GameStatus.SLOW_DOWN_FILE_PATH).toURI().toString()));
    }

    @Override
    public void updateGameStatus(Game game) {
        if (!super.isActive()) {
            game.scaleBallVelocity(0.5);
            super.setActive();
        }
    }

    @Override
    public void resetGameStatus(Game game) {
        game.scaleBallVelocity(2); // back to original speed
    }
}
