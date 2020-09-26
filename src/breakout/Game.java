package breakout;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Game {
    private final GameLauncher gameLauncher;
    private final Ball ball;
    private final Paddle paddle;
    private final KeyInput inputReader;
    private final Block[][] startGridOfBlocks;
    private Block[][] gridOfBlocks;
    private ScoreDisplay scoreDisplay;
    private LivesDisplay livesDisplay;

    public boolean pause = false;

    public Game(GameLauncher gameLauncher, Ball ball, Paddle paddle, Block[][] gridOfBlocks) {
        this.gameLauncher = gameLauncher;
        this.ball = ball;
        this.paddle = paddle;
        this.startGridOfBlocks = this.gridOfBlocks = gridOfBlocks;
        inputReader = new KeyInput(this);
    }

    public void addLivesDisplay(LivesDisplay livesDisplay) {
        this.livesDisplay = livesDisplay;
    }

    public void addScoreDisplay(ScoreDisplay scoreDisplay) {
        this.scoreDisplay = scoreDisplay;
    }

    public void step(double elapsedTime) {
        if (!pause) {
            updateShapes(elapsedTime);
        }
        checkGameStatus();
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
//            root.getChildren().remove(block);
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
//                root.getChildren().remove(gridOfBlocks[i][j]);
            }
        }
    }

    public void setPause() {
        pause = !pause;
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
//        root.getChildren().add(gameMessage);
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

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Block[][] getGridOfBlocks() {
        return gridOfBlocks;
    }

    public KeyInput getInputReader() {
        return inputReader;
    }

    public LivesDisplay getLivesDisplay() {
        return livesDisplay;
    }

    public boolean getPause() {
        return pause;
    }
}
