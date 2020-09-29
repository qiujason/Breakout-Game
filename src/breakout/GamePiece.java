package breakout;

import javafx.scene.shape.Rectangle;

abstract class GamePiece extends Rectangle {
    private int lives;
    private double xVelocity;
    private double yVelocity;

    public GamePiece(double x, double y, double width, double height, int lives) {
        super(x, y, width, height);
        this.lives = lives;
        xVelocity = 0;
        yVelocity = 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void subtractLife() {
        this.lives -= 1;
    }

    public void setMovement(String movement, int row, int col){
        switch (movement) {
            case "sideways" -> setSidewaysMovement(row);
            case "descending" -> setVerticalMovement(col);
        }
    }

    public void updatePosition(double elapsedTime) {
        setX(getX() + xVelocity * elapsedTime);
        setY(getY() + yVelocity * elapsedTime);
    }

    public double getLeft() {
        return getX();
    }

    public double getRight() {
        return getX() + getWidth();
    }

    public double getTop() {
        return getY();
    }

    public double getBottom() {
        return getY() + getHeight();
    }

    public void updateXVelocityUponCollision() {
        reverseXVelocity();
    }

    public void updateYVelocityUponCollision() {
        reverseYVelocity();
    }

    public void setSidewaysMovement(int row) {
        if (row % 2 == 1) {
            setXVelocity(-50);
        } else {
            setXVelocity(50);
        }
    }

    public void setVerticalMovement(int col){
        setYVelocity(-50);
    }

    public int getRowPosition(){
        return Integer.parseInt(this.getId().substring(5,6));
    }

    public int getColPosition(){
        return Integer.parseInt(this.getId().substring(6,7));
    }

    public void setXVelocity(double xVelocity){
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(double yVelocity){
        this.yVelocity = yVelocity;
    }

    public double getXVelocity(){
        return xVelocity;
    }

    public double getYVelocity(){
        return yVelocity;
    }

    private void reverseXVelocity(){
        xVelocity *= -1;
    }

    private void reverseYVelocity(){
        yVelocity *= -1;
    }

    public String toString(){
        if(this instanceof PowerUp){
            return "PowerUp: " + this.getLives() + " " + ((PowerUp) this).isActive() + " " + + this.getYVelocity();
        }else return "Block "  + this.getLives() + " " + this.getYVelocity();
    }

    abstract void updateStatus();

}
