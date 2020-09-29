package breakout;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public abstract class PowerUp extends GamePiece {

    private int timer;
    private boolean falling;
    private boolean powerUpActive;

    public PowerUp(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, 2);
        timer = GameStatus.POWER_UP_TIMER;
        powerUpActive = false;
        setFill(new ImagePattern(image));
    }

    public void beginFalling() {
        setYVelocity(50);
        falling = true;
    }

    public void fall(double elapsedTime) {
        updatePosition(elapsedTime);
    }

    public void setActive() {
        powerUpActive = true;
    }

    public boolean isActive() {
        return powerUpActive;
    }

    public boolean isFalling() {
        return falling;
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
