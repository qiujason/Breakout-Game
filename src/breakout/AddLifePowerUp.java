package breakout;

public class AddLifePowerUp extends PowerUp {

    public AddLifePowerUp(double x, double y, double width, double height) {
        super(x, y, width, height, 1);
    }

    @Override
    public void updateGameStatus(Game game) {
        game.addLife();
    }
}
