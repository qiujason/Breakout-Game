package breakout;

public class SlowDownPowerUp extends PowerUp {

    public SlowDownPowerUp(double x, double y) {
        super(x, y, 1);
    }

    @Override
    public void updateGameStatus(Game game) {
        game.scaleBallVelocity(0.5);
    }
}
