package breakout;

import javafx.scene.shape.Rectangle;


abstract class PowerUp extends Rectangle implements GamePiece {

    private int lives;

    public PowerUp(double x, double y, int lives) {
        super(x, y, GamePiece.WIDTH, GamePiece.HEIGHT);
        setLives(lives);
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public void updateStatus() {
        this.lives -= 1;
    }

    abstract void updateGameStatus(Game game);
}
