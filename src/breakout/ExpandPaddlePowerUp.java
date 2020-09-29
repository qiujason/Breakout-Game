package breakout;

import javafx.scene.image.Image;

import java.io.File;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y, double width, double height) {
        super(x, y, width, height, new Image(new File(GameStatus.EXPAND_PADDLE_FILE_PATH).toURI().toString()));
    }

    @Override
    public void updateGameStatus(Game game) {
        if (!super.isActive()) {
            game.scalePaddleSize(1.5);
            super.setActive();
        }
    }

    @Override
    public void resetGameStatus(Game game) {
        game.scalePaddleSize(1.0/1.5); // back to original size
    }
}
