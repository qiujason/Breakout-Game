package breakout;

public class AddLifePowerUp extends PowerUp {

    public AddLifePowerUp(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void updateGameStatus(Game game) {
        if (!super.isActive()) {
            game.addLife();
            super.setActive();
        }
    }

    @Override
    public void resetGameStatus(Game game) {} // not timer dependent
}
