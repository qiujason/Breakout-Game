package breakout;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Game {
    private final GameLauncher gameLauncher;
    private final Ball ball;
    private final Paddle paddle;
    private final ScoreDisplay scoreDisplay;
    private final LivesDisplay livesDisplay;
    private final LevelDisplay levelDisplay;
    private final HighScoreDisplay highScoreDisplay;
    private GamePiece[][] gridOfGamePieces;
    private int level;
    private List<PowerUp> activePowerUps; // contains power ups that are falling or have been activated
    private boolean pause;
    private boolean powerUpPause;

    public Game(GameLauncher gameLauncher, LivesDisplay livesDisplay, ScoreDisplay scoreDisplay,
                LevelDisplay levelDisplay, HighScoreDisplay highScoreDisplay, Ball ball,
                Paddle paddle, GamePiece[][] gridOfGamePieces) {
        this.gameLauncher = gameLauncher;
        this.livesDisplay = livesDisplay;
        this.scoreDisplay = scoreDisplay;
        this.levelDisplay = levelDisplay;
        this.highScoreDisplay = highScoreDisplay;
        this.ball = ball;
        this.paddle = paddle;
        this.gridOfGamePieces = gridOfGamePieces;
        this.level = GameStatus.FIRST_LEVEL;
        this.activePowerUps = new ArrayList<>();
        this.pause = false;
        this.powerUpPause = true;
    }

    public void handleMouseInput(double x) {
        if (ball.getXVelocity() == 0 && ball.getYVelocity() == 0) {
            ball.setXVelocity(x - GameStatus.WINDOWWIDTH/2.0);
            ball.setYVelocity(-150);
            powerUpPause = false;
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
            if (!powerUpPause) {
                updatePowerUps(elapsedTime);
            }
        }
        checkGameStatus();
    }

    public void resetLevel()  {
        resetBallPaddle();
        clearLevel();
        gridOfGamePieces = gameLauncher.setUpLevel(level);
        scoreDisplay.resetDisplayValue();
    }

    public void resetBallPaddle(){
        ball.reset();
        paddle.reset();
        powerUpPause = true;
    }

    public void clearLevel() {
        for (GamePiece[] rowOfGamePieces : gridOfGamePieces) {
            for (GamePiece gamePiece : rowOfGamePieces) {
                gamePiece.setLives(0);
                gameLauncher.removeFromRoot((Node) gamePiece);
            }
        }
    }

    public void setPause() {
        pause = !pause;
    }

    public void scalePaddleSize(double factor) {
        paddle.setWidth(paddle.getWidth() * factor);
    }

    public void addLife() {
        livesDisplay.addLife();
    }

    public void scaleBallVelocity(double factor) {
        ball.setXVelocity(ball.getXVelocity() * factor);
        ball.setYVelocity(ball.getYVelocity() * factor);
    }

    private void updateShapes(double elapsedTime) {
        checkPaddleCollision();
        checkBallGamePieceCollision();
        checkBorderBallCollision();
        checkBorderBlockCollision(gridOfGamePieces);
        updateBlockPositions(elapsedTime);
        ball.updatePosition(elapsedTime);
    }

    private void updateBlockPositions(double elapsedTime){
        for (GamePiece[] rowOfGamePieces : gridOfGamePieces) {
            for (GamePiece gamePiece : rowOfGamePieces) {
                gamePiece.updatePosition(elapsedTime);
            }
        }
    }

    private void checkPaddleCollision() {
        if (isIntersectingWithBall(paddle)) {
            ball.updateVelocityUponCollision(paddle);
        }
        PowerUp activePowerUp = getActivePowerUpFromCollision();
        if (activePowerUp != null) {
            activePowerUp.updateGameStatus(this);
            activePowerUp.setActive(true);
            updateGamePieceStatus(activePowerUp, -1, -1);
        }
    }

    private PowerUp getActivePowerUpFromCollision() {
        for (PowerUp activePowerUp : activePowerUps) {
            if (activePowerUp.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
                return activePowerUp;
            }
        }
        return null;
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

    private void checkBallGamePieceCollision() {
        for (int i = 0; i < gridOfGamePieces.length; i++) {
            for (int j = 0; j < gridOfGamePieces[i].length; j++) {
                GamePiece gamePiece = gridOfGamePieces[i][j];
                if (gamePiece instanceof PowerUp && activePowerUps.contains(gamePiece)) {
                    continue; // avoid collision of ball with falling power up
                }
                if (gamePiece.getLives() > 0 && isIntersectingWithBall((Rectangle) gamePiece)) {
                    ball.updateVelocityUponCollision((Rectangle) gamePiece);
                    updateGamePieceStatus(gamePiece, i, j);
                    scoreDisplay.increaseScore();
                    highScoreDisplay.updateHighScore(scoreDisplay.getScore());
                }
            }
        }
    }

    private void updateGamePieceStatus(GamePiece gamePiece, int i, int j) {
        gamePiece.updateStatus();
        if (gamePiece instanceof PowerUp && !((PowerUp)gamePiece).isActive()) { // power up is not active or falling
            PowerUp powerUp = (PowerUp)gamePiece;
            powerUp.beginDropDown();
            activePowerUps.add(powerUp); // put power up in to begin falling but has not been activated
        }
        if (gamePiece.getLives() == 0) {
            gameLauncher.removeFromRoot(gamePiece);
            if (gamePiece instanceof Block) {
                generatePowerUp((Block)gamePiece, i, j);
            }
        }
    }

    private void generatePowerUp(Block deletedBlock, int i, int j) {
        if (Math.random() <= GameStatus.POWER_UP_PROBABILITY) {
            Random random = new Random();
            try {
                //get random powerup class
                Class<? extends PowerUp> powerUpClass = GameStatus.POWERUPS.get(random.nextInt(GameStatus.POWERUPS.size()));
                Constructor<? extends PowerUp> powerUpConstructor = powerUpClass.getConstructor(double.class, double.class, double.class, double.class);
                PowerUp powerUp = powerUpConstructor.newInstance(deletedBlock.getX() + deletedBlock.getWidth()/4, deletedBlock.getY(),
                        deletedBlock.getWidth()/2, deletedBlock.getHeight()); // half size of block and move it to center
                powerUp.setId("block" + i + j);
                gameLauncher.addToRoot(powerUp);
                gridOfGamePieces[i][j] = powerUp;
            } catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    private void checkBorderBallCollision() {
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

    private void checkBorderBlockCollision(GamePiece[][] gridOfGamePieces){
        for (int i = 0; i < gridOfGamePieces.length; i++){
            for (int j = 0; j < gridOfGamePieces[0].length; j++){
                GamePiece block = gridOfGamePieces[i][j];
                    if (block.getLives() > 0 && (block.getLeft() <= 0 || block.getRight() >= GameStatus.WINDOWWIDTH)) {
                        changeXVelRow(block);
                        break;
                    }else{
                        GamePiece columnBlock = gridOfGamePieces[j][i];
                        if (columnBlock.getLives() > 0 && (columnBlock.getTop() <= GameStatus.DISPLAYHEIGHT  ||
                                columnBlock.getBottom() >= 400)) {
                            changeYVelCol(columnBlock);
                            break;
                        }
                    }

            }
        }

    }

    private void changeXVelRow(GamePiece block){
        int row = block.getRowPosition();
        for (int i = 0; i < gridOfGamePieces[row].length; i++){
            GamePiece change = gridOfGamePieces[row][i];
            change.updateXVelocityUponCollision();
        }
    }

    private void changeYVelCol(GamePiece block){
        int col = block.getColPosition();
        for (GamePiece[] gridOfGamePiece : gridOfGamePieces) {
            GamePiece change = gridOfGamePiece[col];
            change.updateYVelocityUponCollision();
        }
    }

    private boolean isIntersectingWithBall(Rectangle gamePiece) {
        return gamePiece.getBoundsInParent().intersects(ball.getBoundsInParent());
    }

    private void updatePowerUps(double elapsedTime) {
        Iterator<PowerUp> iterator = activePowerUps.iterator();
        while (iterator.hasNext()) { // iterator has to be used instead in order to avoid Concurrent Modification errors
            PowerUp activePowerUp = iterator.next();
            if (activePowerUp.isActive()) {
                activePowerUp.decrementTimer();
                if (activePowerUp.hasTimerEnded()) {
                    activePowerUp.resetGameStatus(this);
                    iterator.remove(); // remove power up from active power up collection
                }
            } else { // not active but falling
                activePowerUp.dropDown(elapsedTime);
            }
        }
    }

    private void checkGameStatus() {
        Text gameMessage = new Text(200, 300, "");
        if (hasWon()) {
            gameMessage.setText("You Passed This Level!");
            gameMessage.setId("winMessage");
            loadNextLevel();
        } else if (hasLost()) {
            gameMessage.setText("You Ran Out Of Lives! You lost!");
            gameMessage.setId("lossMessage");
            pause = true;
            gameLauncher.addToRoot(gameMessage);
        }
    }

    private boolean hasWon(){
        for (GamePiece[] rowOfGamePieces : gridOfGamePieces){
            for (GamePiece gamePiece : rowOfGamePieces){
                if (gamePiece.getLives() != 0){
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
            if (ball.notInMotion()) {
                ball.setCenterX(ball.getCenterX() - GameStatus.PADDLEDELTA);
            }
        }
    }

    private void handleRightPress() {
        if (!pause && paddle.getX() + paddle.getWidth() <= GameStatus.WINDOWWIDTH - GameStatus.PADDLEDELTA) {
            paddle.setX(paddle.getX() + GameStatus.PADDLEDELTA);
            if (ball.notInMotion()) {
                ball.setCenterX(ball.getCenterX() + GameStatus.PADDLEDELTA);
            }
        }
    }

    private void cheatKeys(KeyCode code) {
        switch (code) {
            case R -> resetLevel();
            case SPACE -> setPause();
            case L -> addLife();
//            case P -> ;
            case C -> clearLevel();
            case D -> clearFirstBlock();
            case S -> highScoreDisplay.clearHighScore();
            case F -> allBlocksLoseLife();
            case G -> clearFirstRowBlock();
            case DIGIT1 -> jumpToLevel(1);
            case DIGIT2 -> jumpToLevel(2);
            case DIGIT3 -> jumpToLevel(3);
            case DIGIT4 -> jumpToLevel(4);
            case DIGIT5 -> jumpToLevel(5);

        }
    }

    private void clearFirstRowBlock(){
        GamePiece[] rowToRemove = getFirstRowBlock();
        for(GamePiece block : rowToRemove){
            block.setLives(0);
            gameLauncher.removeFromRoot(block);
        }
    }

    private void clearFirstBlock(){
        GamePiece blockToRemove = getFirstBlock();
        blockToRemove.setLives(0);
        gameLauncher.removeFromRoot(blockToRemove);
    }

    private void allBlocksLoseLife(){
        for (int i = 0; i < gridOfGamePieces.length; i++){
            for (int j = 0; j < gridOfGamePieces[0].length; j++){
                if (gridOfGamePieces[i][j].getLives() > 0){
                    updateGamePieceStatus(gridOfGamePieces[i][j], i, j);
                }
            }
        }
    }

    private void jumpToLevel(int level) {
        clearLevel();
        this.level = level;
        gridOfGamePieces = gameLauncher.setUpLevel(level);
        levelDisplay.setLevel(level);
    }

    private GamePiece getFirstBlock(){
        for (int i = gridOfGamePieces.length - 1; i >= 0; i--){
            for (int j = 0; j <gridOfGamePieces[0].length; j++){
                if (gridOfGamePieces[i][j].getLives() > 0){
                    return gridOfGamePieces[i][j];
                }
            }
        }
        return null;
    }

    private GamePiece[] getFirstRowBlock(){
        for (int i = gridOfGamePieces.length - 1; i >= 0; i--){
            for (int j = 0; j <gridOfGamePieces[0].length; j++){
                if (gridOfGamePieces[i][j].getLives() > 0){
                    return gridOfGamePieces[i];
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
        gridOfGamePieces = gameLauncher.setUpLevel(level);
        scoreDisplay.setCheckPointScore();
        levelDisplay.incrementLevel();
        resetBallPaddle();
    }
}
