//package breakout;
//
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.input.KeyCode;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.Test;
//import util.DukeApplicationTest;
//
//import java.io.FileNotFoundException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class GameTest extends DukeApplicationTest {
//    private final GameLauncher myGame = new GameLauncher();
//    private final BlockConfigurationReader reader = new BlockConfigurationReader();
//
//    private Ball myBall;
//    private Paddle myPaddle;
//    private Scene myScene;
//
//    public void start(Stage stage) throws Exception {
//        // attach scene to the stage and display it
//        myScene = myGame.setupScene(GameStatus.WINDOWWIDTH, GameStatus.WINDOWHEIGHT, GameStatus.BACKGROUND);
//        stage.setScene(myScene);
//        stage.show();
//
//        myBall = lookup("#ball").query();
//        myPaddle = lookup("#paddle").query();
//    }
//
//    @Test
//    public void testInitialBallPosition(){
//        assertEquals(250, myBall.getCenterX());
//        assertEquals(574, myBall.getCenterY());
//    }
//
//    @Test
//    public void testInitialBallSize(){
//        assertEquals(10, myBall.getRadius());
//    }
//
//    @Test
//    public void testInitialBallVelocity(){
//        assertEquals(0, myBall.getXVelocity());
//        assertEquals(0, myBall.getYVelocity());
//    }
//
//    @Test
//    public void testInitialPaddlePosition(){
//        assertEquals(200, myPaddle.getX());
//        assertEquals(585, myPaddle.getY());
//    }
//
//    @Test
//    public void testInitialPaddleSize(){
//        assertEquals(100, myPaddle.getWidth());
//        assertEquals(15, myPaddle.getHeight());
//    }
//
//    @Test
//    public void testPaddleMove(){
//        double originalPosition = myPaddle.getStartX();
//        press(myScene, KeyCode.RIGHT);
//        assertTrue(myPaddle.getX() > originalPosition);
//    }
//
//    @Test
//    public void testKeyBlocks() throws FileNotFoundException {
//        Group root = new Group();
//        Block[][] gridOfBlocks = reader.loadLevel(root, 1);
//        assertEquals(10, gridOfBlocks[0][0].getX());
//        assertEquals(28, gridOfBlocks[0][0].getY());
//        assertEquals(10, gridOfBlocks[1][0].getX());
//        assertEquals(74, gridOfBlocks[1][0].getY());
//    }
//
//    @Test
//    public void testCornerHit(){
//        myBall.setCenterX(50);
//        myBall.setCenterY(60);
//        myBall.setXVelocity(-25);
//        myBall.setYVelocity(-30);
//
//        for(int i=0; i < 2; i++){
//            javafxRun(() -> myGame.step(1));
//        }
//
//        myBall.setCenterX(50);
//        myBall.setCenterY(60);
//        myBall.setXVelocity(-25);
//        myBall.setYVelocity(-30);
//
//        for(int i=0; i < 4; i++){
//            javafxRun(() -> myGame.step(1));
//        }
//
//        assertEquals(25, myBall.getXVelocity());
//        assertEquals(30, myBall.getYVelocity());
//    }
//
//    @Test
//    public void testPaddleHit(){
//        myBall.setCenterX(250);
//        myBall.setCenterY(300);
//        myBall.setXVelocity(0);
//        myBall.setYVelocity(-100);
//
//        for(int i=0; i < 4; i++){
//            javafxRun(() -> myGame.step(1));
//        }
//
//        assertEquals(0, myBall.getXVelocity());
//        assertEquals(100, myBall.getYVelocity());
//    }
//
//    @Test
//    public void testBottomHit(){
//        myBall.setCenterX(100);
//        myBall.setCenterY(300);
//        myBall.setXVelocity(0);
//        myBall.setYVelocity(30);
//
//        for(int i=0; i < 12; i++){
//            javafxRun(() -> myGame.step(1));
//        }
//
//        assertEquals(myBall.getCenterX(), myBall.getStartX());
//        assertEquals(myBall.getCenterY(), myBall.getStartY());
//    }
//
//    @Test
//
//    public void testBallHitBounces() {
//        myBall.setCenterX(50);
//        myBall.setCenterY(300);
//        myBall.setXVelocity(0);
//        myBall.setYVelocity(-250);
//
//        for (int i = 0; i < 12; i++) {
//            javafxRun(() -> myGame.step(GameStatus.SECOND_DELAY));
//        }
//
//        assertEquals(0, myBall.getXVelocity());
//        assertEquals(250, myBall.getYVelocity());
//    }
//
//    @Test
//    public void testBallHitUpdateScore() {
//        myBall.setCenterX(50);
//        myBall.setCenterY(300);
//        myBall.setXVelocity(0);
//        myBall.setYVelocity(-250);
//
//        for (int i = 0; i < 12; i++) {
//            javafxRun(() -> myGame.step(GameStatus.SECOND_DELAY));
//        }
//
//        assertEquals(110, myGame.scoreDisplay.getScore());
//    }
//
//    @Test
//    public void testBallHitDeleteBlock() {
//        myBall.setCenterX(50);
//        myBall.setCenterY(300);
//        myBall.setXVelocity(0);
//        myBall.setYVelocity(-250);
//
//        for (int i = 0; i < 12; i++) {
//            javafxRun(() -> myGame.step(GameStatus.SECOND_DELAY));
//        }
//
//        Exception e = assertThrows(Exception.class, () -> lookup("#block40").query());
//        assertEquals("there is no node in the scene-graph matching the query: NodeQuery: from nodes:", e.getMessage().substring(0, 78));
//    }
//
//    @Test
//    public void testLevelClear(){
//        press(myScene, KeyCode.C);
//        javafxRun(() -> myGame.step(GameStatus.SECOND_DELAY));
//        Text winMessage = lookup("#winMessage").query();
//        assertTrue(myScene.getRoot().getChildrenUnmodifiable().contains(winMessage));
//    }
//
//    @Test
//    public void testLevelLost(){
//        for(int i=0; i < 30; i++){
//            click(myScene, 200, 500);
//            press(myScene, KeyCode.RIGHT);
//            javafxRun(() -> myGame.step(1));
//        }
//        Text lossMessage = lookup("#lossMessage").query();
//        assertTrue(myScene.getRoot().getChildrenUnmodifiable().contains(lossMessage));
//    }
//}