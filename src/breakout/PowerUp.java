package breakout;


abstract class PowerUp extends GamePiece {

    private int timer;
    private boolean powerUpActive;

    public PowerUp(double x, double y, double width, double height) {
        super(x, y, width, height, 2);
        timer = GameStatus.POWER_UP_TIMER;
        powerUpActive = false;
    }

    public void beginDropDown() {
        setYVelocity(50);
    }

    public void dropDown(double elapsedTime) {
        updatePosition(elapsedTime);
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

    public void getAttributesFromBlock(Block block) {
        setId(block.getId());
        setXVelocity(block.getXVelocity());
        setYVelocity(block.getYVelocity());
    }

    abstract void updateGameStatus(Game game);

    abstract void resetGameStatus(Game game);
}
