package breakout;

public class SlowDownPowerUp extends PowerUp {

    public SlowDownPowerUp(double x, double y, double width, double height) {
        super(x, y, width, height, 2);
    }

    @Override
    public void updateGameStatus(Game game) {
        if (!super.isActive()) {
            game.scaleBallVelocity(0.5);
            super.setActive(true);
        }
    }

    @Override
    public void resetGameStatus(Game game) {
        game.scaleBallVelocity(2); // back to original speed
    }
}
