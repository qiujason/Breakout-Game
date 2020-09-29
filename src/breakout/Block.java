package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle implements GamePiece {

    //TODO: make into better data structure
    private final Paint ONE_HIT_BLOCK_COLOR = Color.web("#bae1ff");
    private final Paint TWO_HIT_BLOCK_COLOR = Color.web("#ffb3ba");
    private final Paint THREE_HIT_BLOCK_COLOR = Color.web("#baffc9");

    private int lives;
    private String movement;
    private double xVel;
    private double yVel;

    public Block(double x, double y, double width, double height, int lives, String movement) {
        super(x, y, width, height);
        this.lives = lives;
        setFill(determineColor(lives));
        movement = movement;
        xVel = yVel = 0;
    }

    public Paint determineColor(int lives) {
        Paint color = switch (lives) {
            case 1 -> ONE_HIT_BLOCK_COLOR;
            case 2 -> TWO_HIT_BLOCK_COLOR;
            case 3 -> THREE_HIT_BLOCK_COLOR;
            default -> THREE_HIT_BLOCK_COLOR;
        };
        return color;
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
        setFill(determineColor(lives));
    }

    public String toString(){
        return Integer.toString(lives);
    }

    private void setXVel(double xVel){
        this.xVel = xVel;
    }

    private void setYVel(double yVel){
        this.yVel = yVel;
    }

    public void setMovement(String movement, int row, int col){
        switch (movement) {
            case "sideways" -> setSidewaysMovement(row);
            case "descending" -> setVerticalMovement(col);
        }
    }

    @Override
    public void updatePosition(double elapsedTime) {
        setX(getX() + xVel * elapsedTime);
        setY(getY() + yVel * elapsedTime);
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
        return getY() - getHeight();
    }

    public void updateXVelocityUponCollision() {
        reverseXVel();
    }

    public void updateYVelocityUponCollision() {
        reverseYVel();
    }

    private void reverseXVel(){
        xVel*= -1;
    }

    private void reverseYVel(){
        yVel*= -1;
    }


    public void setSidewaysMovement(int row){
        if (row % 2 == 1){
            setXVel(-50);
        }else{
            setXVel(50);
        }
    }

    public void setVerticalMovement(int col){
        setYVel(-50);
    }

    public int getRowPosition(){
        return Integer.parseInt(this.getId().substring(5,6));
    }

    public int getColPosition(){
        return Integer.parseInt(this.getId().substring(6,7));
    }

}

