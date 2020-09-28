package breakout;

public interface GamePiece {

    double WIDTH = GameStatus.GAME_PIECE_WIDTH;
    double HEIGHT = GameStatus.GAME_PIECE_HEIGHT;

    int getLives();

    void setLives(int lives);

    void updateStatus();
}
