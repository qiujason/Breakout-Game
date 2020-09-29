package breakout;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


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
    private List<PowerUp> activePowerUps; // contains power ups that have been activated
    private List<PowerUp> fallingPowerUps; // contains power ups that are falling
    private boolean pause;

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
        this.fallingPowerUps = new ArrayList<>();
        this.activePowerUps = new ArrayList<>();
        this.pause = false;
    }

    public void handleMouseInput(double x) {
        if (ball.getXVelocity() == 0 && ball.getYVelocity() == 0) {
            ball.setXVelocity(x - GameStatus.WINDOWWIDTH/2.0);
            ball.setYVelocity(-150);
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
        gridOfGamePieces = gameLauncher.setUpLevel(level);
        scoreDisplay.resetDisplayValue();
    }

    public void resetBallPaddle(){
        ball.reset();
        paddle.reset();
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
        checkBorderBlockCollision();
        updateBlockPositions(elapsedTime);
        updatePowerUps(elapsedTime);
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
        for (PowerUp fallingPowerUp : fallingPowerUps) {
            if (fallingPowerUp != null && isIntersectingWithPaddle(fallingPowerUp)){
                fallingPowerUp.updateGameStatus(this);
                updateGamePieceStatus(fallingPowerUp);
            }
        }
    }

    private void checkBallGamePieceCollision() {
        for (int i = 0; i < gridOfGamePieces.length; i++) {
            for (int j = 0; j < gridOfGamePieces[i].length; j++) {
                GamePiece gamePiece = gridOfGamePieces[i][j];
                if (gamePiece instanceof PowerUp && fallingPowerUps.contains(gamePiece)) {
                    continue; // avoid collision of ball with falling power up
                }
                else if (gamePiece.getLives() > 0 && isIntersectingWithBall(gamePiece)) {
                    ball.updateVelocityUponCollision(gamePiece);
                    scoreDisplay.increaseScore();
                    highScoreDisplay.updateHighScore(scoreDisplay.getScore());
                    updateGamePieceStatus(gamePiece);
                }

            }
        }
    }

    private void updateGamePieceStatus(GamePiece gamePiece) {
        gamePiece.updateStatus();
        if (gamePiece instanceof PowerUp && !((PowerUp)gamePiece).isFalling()) { // power up is not active or falling
           gridOfGamePieces[gamePiece.getRowPosition()][gamePiece.getColPosition()] =
                    new Block(0,0,1,1,0, "none");
           PowerUp powerUp = (PowerUp)gamePiece;
           powerUp.beginFalling();
           fallingPowerUps.add(powerUp); // put power up in to begin falling
        }
        if (gamePiece.getLives() <= 0) {
            gameLauncher.removeFromRoot(gamePiece);
            if (gamePiece instanceof Block) {
                generatePowerUp((Block)gamePiece, gamePiece.getRowPosition(), gamePiece.getColPosition());
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
                powerUp.getAttributesFromBlock(deletedBlock);
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

    private void checkBorderBlockCollision(){
        for (int i = 0; i < gridOfGamePieces.length; i++){
            for (int j = 0; j < gridOfGamePieces[0].length; j++){
                GamePiece block = gridOfGamePieces[i][j];
                if (block.getLives() > 0 && (block.getLeft() <= 0 || block.getRight() >= GameStatus.WINDOWWIDTH)) {
                    changeXVelRow(block);
                    break;
                }
            }
        }
        for (int i = 0; i < gridOfGamePieces[0].length; i++) {
            for (int j = 0; j < gridOfGamePieces.length; j++) {
                GamePiece columnBlock = gridOfGamePieces[j][i];
                if (columnBlock.getLives() > 0 && (columnBlock.getTop() <= GameStatus.DISPLAYHEIGHT  ||
                        columnBlock.getBottom() >= 300)) {
                    changeYVelCol(columnBlock);
                    break;
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
            if (change instanceof PowerUp && ((PowerUp) change).isFalling()){
                return;
            }
            change.updateYVelocityUponCollision();

        }
    }

    private boolean isIntersectingWithBall(Rectangle gamePiece) {
        return gamePiece.getBoundsInParent().intersects(ball.getBoundsInParent());
    }

    private boolean isIntersectingWithPaddle(Rectangle gamePiece){
        return gamePiece.getBoundsInParent().intersects(paddle.getBoundsInParent());
    }

    private void updatePowerUps(double elapsedTime){
        updateActivePowerUps();
        updateFallingPowerUps(elapsedTime);
    }

    private void updateActivePowerUps() {
        Iterator<PowerUp> iterator = activePowerUps.iterator();
        while (iterator.hasNext()) { // iterator has to be used instead in order to avoid Concurrent Modification errors
            PowerUp activePowerUp = iterator.next();
            if (activePowerUp.isActive()) {
                activePowerUp.decrementTimer();
                if (activePowerUp.hasTimerEnded()) {
                    activePowerUp.resetGameStatus(this);
                    iterator.remove(); // remove power up from active power up collection
                }
            }
        }
    }

    private void updateFallingPowerUps(double elapsedTime){
        Iterator<PowerUp> iterator = fallingPowerUps.iterator();
        while (iterator.hasNext()) { // iterator has to be used instead in order to avoid Concurrent Modification errors
            PowerUp fallingPowerUp = iterator.next();
            fallingPowerUp.fall(elapsedTime);
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
                    updateGamePieceStatus(gridOfGamePieces[i][j]);
                }
            }
        }
    }

    private void jumpToLevel(int level) {
        clearLevel();
        resetBallPaddle();
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
