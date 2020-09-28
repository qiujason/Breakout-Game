package breakout;

public class AddLifePowerUp extends PowerUp {

    public AddLifePowerUp(double x, double y) {
        super(x, y, 1);
    }

    @Override
    public void updateGameStatus(Game game) {
        game.addLife();
    }
}
