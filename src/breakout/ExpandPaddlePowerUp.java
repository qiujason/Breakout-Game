package breakout;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y, double width, double height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void updateGameStatus(Game game) {
        game.scalePaddleSize(1.5);
    }
}
