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

    public void setMovement(String movement, int x, int y){
        return;
    }

    public void updatePosition(double elapsedTime){
        return;
    }

    public double getLeft(){
        return this.getX();
    }

    public double getRight(){
        return this.getX() + this.getArcWidth();
    }

    public double getTop(){
        return this.getY();
    }

    public double getBottom(){
        return this.getX() + this.getArcHeight();
    }

    public void updateXVelocityUponCollision(){
        return;
    }

    public void updateYVelocityUponCollision(){
        return;
    }
}
