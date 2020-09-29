package breakout;

import javafx.scene.shape.Rectangle;


abstract class PowerUp extends Rectangle implements GamePiece {

    private int lives;
    private int timer;
    private boolean powerUpActive;

    public PowerUp(double x, double y, double width, double height, int lives) {
        super(x, y, width, height);
        setLives(lives);
        timer = GameStatus.POWER_UP_TIMER;
        powerUpActive = false;
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

    public void dropDown(double elapsedTime) {
        setY(getY() + 50 * elapsedTime);
    }

    public void setActive(boolean isActive) {
        powerUpActive = isActive;
    }

    public boolean isActive() {
        return powerUpActive;
    }

    public void decrementTimer() {
        timer -= 1;
    }

    public boolean hasTimerEnded() {
        return timer <= 0;
    }

    abstract void updateGameStatus(Game game);

    abstract void resetGameStatus(Game game);
}
