package breakout;

public class SlowDownPowerUp extends PowerUp {

    public SlowDownPowerUp(double x, double y, double width, double height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void updateGameStatus(Game game) {
        game.scaleBallVelocity(0.5);
    }
}
