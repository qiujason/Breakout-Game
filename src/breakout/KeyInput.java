package breakout;

import javafx.scene.input.KeyCode;

public class KeyInput {
    private final Game game;
    private final Ball ball;
    private final Paddle paddle;

    public KeyInput(Game game) {
        this.game = game;
        this.ball = game.getBall();
        this.paddle = game.getPaddle();
    }

    public void handleMouseInput(double x, double y) {
        if (ball.getXVelocity() == 0 && ball.getYVelocity() == 0) {
            ball.setXVelocity(x - GameStatus.WINDOWWIDTH/2.0);
            ball.setYVelocity(-250);
        }
    }

    public void handleKeyInput(KeyCode code) {
        cheatKeys(code);
        switch (code) {
            case LEFT -> handleLeftPress();
            case RIGHT -> handleRightPress();
        }
    }

    private void handleLeftPress() {
        if (!game.getPause() && paddle.getX() >= GameStatus.PADDLEDELTA) {
            paddle.setX(paddle.getX() - GameStatus.PADDLEDELTA);
            if (!ball.getInMotion()) {
                ball.setCenterX(ball.getCenterX() - GameStatus.PADDLEDELTA);
            }
        }
    }

    private void handleRightPress() {
        if (!game.getPause() && paddle.getX() + GameStatus.PADDLEWIDTH <= GameStatus.WINDOWWIDTH - GameStatus.PADDLEDELTA) {
            paddle.setX(paddle.getX() + GameStatus.PADDLEDELTA);
            if (!ball.getInMotion()) {
                ball.setCenterX(ball.getCenterX() + GameStatus.PADDLEDELTA);
            }
        }
    }

    public void cheatKeys(KeyCode code) {
        switch (code) {
            case R -> game.reset();
            case SPACE -> game.setPause();
            case L -> game.getLivesDisplay().addLife();
//            case P -> ;
            case C -> game.clearLevel();
        }
    }
}
