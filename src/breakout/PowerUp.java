package breakout;

import javafx.scene.shape.Rectangle;


abstract class PowerUp extends Rectangle implements GamePiece {

    private int lives;

    public PowerUp(double x, double y, double width, double height, int lives) {
        super(x, y, width, height);
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
