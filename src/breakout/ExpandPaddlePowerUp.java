package breakout;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y, 1);
    }

    @Override
    public void updateGameStatus(Game game) {
        game.scalePaddleSize(1.5);
    }
}
