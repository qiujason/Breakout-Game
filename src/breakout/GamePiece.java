package breakout;

public interface GamePiece {

    int getLives();

    void setLives(int lives);

    void updateStatus();

    void setMovement(String movement, int row, int col);

    void updatePosition(double elapsedTime);

//    double getLeft();
//
//    double getRight();
//
//    double getTop();
//
//    double getBottom();
//
//    void updateXVelocityUponCollision();
//
//    void updateYVelocityUponCollision();

}
