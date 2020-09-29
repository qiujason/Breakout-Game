package breakout;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y, double width, double height) {
        super(x, y, width, height, 2);
    }

    @Override
    public void updateGameStatus(Game game) {
        if (!super.isActive()) {
            game.scalePaddleSize(1.5);
            super.setActive(true);
        }
    }

    @Override
    public void resetGameStatus(Game game) {
        game.scalePaddleSize(1.0/1.5); // back to original size
    }
}
