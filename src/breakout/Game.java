package breakout;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Game {
    private final GameLauncher gameLauncher;
    private final Ball ball;
    private final Paddle paddle;
    private final Block[][] startGridOfBlocks;
    private Block[][] gridOfBlocks;
    private ScoreDisplay scoreDisplay;
    private LivesDisplay livesDisplay;
    private boolean pause = false;

    public Game(GameLauncher gameLauncher, LivesDisplay livesDisplay, ScoreDisplay scoreDisplay,
                Ball ball, Paddle paddle, Block[][] gridOfBlocks) {
        this.gameLauncher = gameLauncher;
        this.livesDisplay = livesDisplay;
        this.scoreDisplay = scoreDisplay;
        this.ball = ball;
        this.paddle = paddle;
        this.startGridOfBlocks = gridOfBlocks;
        this.gridOfBlocks = gridOfBlocks;
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

    public void step(double elapsedTime) {
        if (!pause) {
            updateShapes(elapsedTime);
        }
        checkGameStatus();
    }

    public void reset() {
        ball.reset();
        paddle.reset();
        gridOfBlocks = startGridOfBlocks;
    }

    public void clearLevel() {
        for (int i = 0; i < gridOfBlocks.length; i++) {
            for (int j = 0; j < gridOfBlocks[0].length; j++) {
                gridOfBlocks[i][j].setLives(0);
                gameLauncher.removeFromRoot(gridOfBlocks[i][j]);
            }
        }
    }

    public void setPause() {
        pause = !pause;
    }

    private void updateShapes(double elapsedTime) {
        checkPaddleCollision();
        checkBorderCollision();
        checkBlockCollision();
        ball.updatePosition(elapsedTime);
    }

    private void checkPaddleCollision() {
        if (isIntersectingWithBall(paddle)) {
            ball.updateVelocityUponCollision(paddle);
        }
    }

    private void checkBlockCollision() {
        for (Block[] gridOfBlock : gridOfBlocks) {
            for (Block currentBlock : gridOfBlock) {
                if (currentBlock.getLives() > 0 && isIntersectingWithBall(currentBlock)) {
                    ball.updateVelocityUponCollision(currentBlock);
                    updateBlockStatus(currentBlock);
                    scoreDisplay.addScore();
                }
            }
        }
    }

    private boolean isIntersectingWithBall(Rectangle gamePiece) {
        return gamePiece.getBoundsInParent().intersects(ball.getBoundsInParent());
    }

    private void updateBlockStatus(Block block) {
        block.subtractLife();
        if (block.getLives() == 0) {
            gameLauncher.removeFromRoot(block);
        }
    }

    private void checkBorderCollision() {
        if (ball.getLeft() <= 0 || ball.getRight() >= GameStatus.WINDOWWIDTH) {
            ball.reverseXVelocity();
        } else if (ball.getTop() <= GameStatus.DISPLAYHEIGHT) {
            ball.reverseYVelocity();
        } else if (ball.getTop() > GameStatus.WINDOWHEIGHT) { // goes below the screen
            reset();
            livesDisplay.subtractLife();
        }
    }

    private void checkGameStatus() {
        Text gameMessage = new Text(200, 300, "");
        if (hasWon()) {
            gameMessage.setText("You Passed This Level!");
            gameMessage.setId("winMessage");
        } else if (hasLost()) {
            gameMessage.setText("You Ran Out Of Lives! You lost!");
            gameMessage.setId("lossMessage");
        } else {
            return;
        }
        pause = true;
        reset();
        gameLauncher.addToRoot(gameMessage);
    }

    private boolean hasWon(){
        for (Block[] row : gridOfBlocks){
            for (Block b : row){
                if (b.getLives() != 0){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasLost(){
        return livesDisplay.getLives() == 0;
    }

    private void handleLeftPress() {
        if (!pause && paddle.getX() >= GameStatus.PADDLEDELTA) {
            paddle.setX(paddle.getX() - GameStatus.PADDLEDELTA);
            if (!ball.getInMotion()) {
                ball.setCenterX(ball.getCenterX() - GameStatus.PADDLEDELTA);
            }
        }
    }

    private void handleRightPress() {
        if (!pause && paddle.getX() + GameStatus.PADDLEWIDTH <= GameStatus.WINDOWWIDTH - GameStatus.PADDLEDELTA) {
            paddle.setX(paddle.getX() + GameStatus.PADDLEDELTA);
            if (!ball.getInMotion()) {
                ball.setCenterX(ball.getCenterX() + GameStatus.PADDLEDELTA);
            }
        }
    }

    private void cheatKeys(KeyCode code) {
        switch (code) {
            case R -> reset();
            case SPACE -> setPause();
            case L -> livesDisplay.addLife();
//            case P -> ;
            case C -> clearLevel();
        }
    }
}
