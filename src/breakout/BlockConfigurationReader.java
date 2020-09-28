package breakout;

import javafx.scene.Group;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class BlockConfigurationReader {

    String directory = "blockfiles/";

    public void readInBlocks(int level, GamePiece[][] gridOfGamePieces) throws FileNotFoundException {
        String filePath = directory + "level" + level + ".txt";
        Scanner scanner = new Scanner(new File(filePath));
        int row = 0;
        while (scanner.hasNextLine()) {
            String[] gamePieceLives = scanner.nextLine().split(" ");
            for (int i = 0; i < gridOfGamePieces[row].length; i++) {
                double xPos = i * (GameStatus.GAME_PIECE_WIDTH + GameStatus.GAP) + GameStatus.GAP;
                double yPos = row * (GameStatus.GAME_PIECE_HEIGHT + GameStatus.GAP) + GameStatus.GAP + GameStatus.DISPLAYHEIGHT;
                gridOfGamePieces[row][i] = new Block(xPos, yPos, Integer.parseInt(gamePieceLives[i]));
            }
            row++;
        }
    }

    public GamePiece[][] loadLevel(Group root, int level) throws FileNotFoundException {
        GamePiece[][] gridOfGamePieces = new GamePiece[getRowNum(level)][getColNum(level)];
        readInBlocks(level, gridOfGamePieces);
        for (int i = 0; i < gridOfGamePieces.length; i++) {
            for (int j = 0; j < gridOfGamePieces[i].length; j++) {
                Block block = (Block)gridOfGamePieces[i][j]; // all game pieces are currently blocks
                if (block.getLives() > 0) {
                    block.setId("block" + i + j);
                    root.getChildren().add(block);
                }
            }
        }
        return gridOfGamePieces;
    }

    private int getRowNum(int level) throws FileNotFoundException {
        String filePath = directory + "level" + level + ".txt";
        Scanner scanner = new Scanner(new File(filePath));
        int numOfRows = 0;
        while (scanner.hasNextLine()) {
            numOfRows++;
            scanner.nextLine();
        }
        return numOfRows;
    }

    private int getColNum(int level) throws FileNotFoundException {
        String filePath = directory + "level" + level + ".txt";
        Scanner scanner = new Scanner(new File(filePath));
        String[] columns = scanner.nextLine().split(" ");
        return columns.length;
    }
}

