package breakout;


abstract class PowerUp extends GamePiece {

    private int timer;
    private boolean powerUpActive;

    public PowerUp(double x, double y, double width, double height, int lives) {
        super(x, y, width, height, lives);
        timer = GameStatus.POWER_UP_TIMER;
        powerUpActive = false;
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

    public void updateStatus() {
        subtractLife();
    }

    abstract void updateGameStatus(Game game);

    abstract void resetGameStatus(Game game);
}
