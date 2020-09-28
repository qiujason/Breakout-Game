package breakout;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;


public class Game {
    private final GameLauncher gameLauncher;
    private final Ball ball;
    private final Paddle paddle;
    private final ScoreDisplay scoreDisplay;
    private final LivesDisplay livesDisplay;
    private final LevelDisplay levelDisplay;
    private final HighScoreDisplay highScoreDisplay;
    private Block[][] gridOfBlocks;
    private boolean pause = false;
    private int level;

    public Game(GameLauncher gameLauncher, LivesDisplay livesDisplay, ScoreDisplay scoreDisplay,
                LevelDisplay levelDisplay, Ball ball, Paddle paddle, Block[][] gridOfBlocks,
                HighScoreDisplay highScoreDisplay) {
        this.gameLauncher = gameLauncher;
        this.livesDisplay = livesDisplay;
        this.scoreDisplay = scoreDisplay;
        this.levelDisplay = levelDisplay;
        this.ball = ball;
        this.paddle = paddle;
        this.gridOfBlocks = gridOfBlocks;
        this.level = GameStatus.FIRST_LEVEL;
        this.highScoreDisplay = highScoreDisplay;
    }

    public void handleMouseInput(double x) {
        if (ball.getXVelocity() == 0 && ball.getYVelocity() == 0) {
            ball.setXVelocity(x - GameStatus.WINDOWWIDTH/2.0);
            ball.setYVelocity(-100);
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

    public void resetLevel()  {
        resetBallPaddle();
        clearLevel();
        gridOfBlocks = gameLauncher.setUpLevel(level);
        scoreDisplay.resetDisplayValue();
    }

    public void resetBallPaddle(){
        ball.reset();
        paddle.reset();
    }

    public void clearLevel() {
        for (Block[] rowOfBlocks : gridOfBlocks) {
            for (Block block : rowOfBlocks) {
                block.setLives(0);
                gameLauncher.removeFromRoot(block);
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
                    scoreDisplay.increaseScore();
                    highScoreDisplay.updateHighScore(scoreDisplay.getScore());
                }
            }
        }
    }

    private void checkBorderCollision() {
        if (ball.getLeft() <= 0 || ball.getRight() >= GameStatus.WINDOWWIDTH) {
            ball.updateXVelocityUponBorderCollision();
        } else if (ball.getTop() <= GameStatus.DISPLAYHEIGHT) {
            ball.updateYVelocityUponBorderCollision();
        } else if (ball.getTop() > GameStatus.WINDOWHEIGHT) { // goes below the screen
            resetBallPaddle();
            livesDisplay.subtractLife();
            scoreDisplay.resetBonus();
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

    private void checkGameStatus() {
        Text gameMessage = new Text(200, 300, "");
        if (hasWon()) {
            gameMessage.setText("You Passed This Level!");
            gameMessage.setId("winMessage");
            loadNextLevel();
            return;
        } else if (hasLost()) {
            gameMessage.setText("You Ran Out Of Lives! You lost!");
            gameMessage.setId("lossMessage");
        } else {
            return;
        }
        pause = true;
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
            case R -> resetLevel();
            case SPACE -> setPause();
            case L -> livesDisplay.addLife();
//            case P -> ;
            case C -> clearLevel();
            case D -> clearFirstBlock();
            case S -> highScoreDisplay.clearHighScore();
            case F -> allBlocksLoseLife();
            case DIGIT1 -> jumpToLevel(1);
            case DIGIT2 -> jumpToLevel(2);
            case DIGIT3 -> jumpToLevel(3);
            case DIGIT4 -> jumpToLevel(4);
            case DIGIT5 -> jumpToLevel(5);

        }
    }

    private void clearFirstBlock(){
        Block blockToRemove = getFirstBlock();
        blockToRemove.setLives(0);
        gameLauncher.removeFromRoot(blockToRemove);
    }

    private void allBlocksLoseLife(){
        for (int i = 0; i < gridOfBlocks.length; i++){
            for (int j = 0; j < gridOfBlocks[0].length; j++){
                if (gridOfBlocks[i][j].getLives() > 0){
                    updateBlockStatus(gridOfBlocks[i][j]);
                }
            }
        }
    }

    private void jumpToLevel(int level) {
        gameLauncher.setUpLevel(level);
    }

    private Block getFirstBlock(){
        for (int i = gridOfBlocks.length - 1; i >= 0; i--){
            for (int j = 0; j <gridOfBlocks[0].length; j++){
                if (gridOfBlocks[i][j].getLives() > 0){
                    return gridOfBlocks[i][j];
                }
            }
        }
        return null;
    }

    private void loadNextLevel(){
        BlockConfigurationReader blockReader = new BlockConfigurationReader();
        int maxLevel = blockReader.getFileCount();
        if (level >= maxLevel){
            Text gameMessage = new Text(150, 300, "WOWOWOW!!! YOU BEAT THE WHOLE GAME");
            gameLauncher.addToRoot(gameMessage);
            resetBallPaddle();
            return;
        }
        clearLevel();
        level += 1;
        gridOfBlocks = gameLauncher.setUpLevel(level);
        scoreDisplay.setCheckPointScore();
        levelDisplay.incrementLevel();
        resetBallPaddle();
    }
}
