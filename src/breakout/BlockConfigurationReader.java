package breakout;

import javafx.scene.Group;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class BlockConfigurationReader {

    String directory = "blockfiles/";

    public void readInBlocks(int level, Block[][] gridOfBlocks) throws FileNotFoundException {
        String filePath = directory + "level" + level + ".txt";
        Scanner scanner = new Scanner(new File(filePath));
        int row = 0;
        while (scanner.hasNextLine()) {
            String[] blockLives = scanner.nextLine().split(" ");
            for (int i = 0; i < gridOfBlocks[row].length; i++) {
                double xPos = i * (GameStatus.BLOCKWIDTH + GameStatus.GAP) + GameStatus.GAP;
                double yPos = row * (GameStatus.BLOCKHEIGHT + GameStatus.GAP) + GameStatus.GAP + GameStatus.DISPLAYHEIGHT;
                gridOfBlocks[row][i] = new Block(xPos, yPos, GameStatus.BLOCKWIDTH, GameStatus.BLOCKHEIGHT, Integer.parseInt(blockLives[i]));
            }
            row++;
        }
    }

    public Block[][] loadLevel(Group root, int level) throws FileNotFoundException {
        Block[][] gridOfBlocks = new Block[getRowNum(level)][getColNum(level)];
        readInBlocks(level, gridOfBlocks);
        for (int i = 0; i < gridOfBlocks.length; i++) {
            for (int j = 0; j < gridOfBlocks[i].length; j++) {
                Block block = gridOfBlocks[i][j];
                if (block.getLives() > 0) {
                    block.setId("block" + i + j);
                    root.getChildren().add(block);
                }
            }
        }
        return gridOfBlocks;
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

