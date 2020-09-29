package breakout;

import java.util.Objects;
import javafx.scene.Group;

import java.io.File;
import java.util.Scanner;


public class BlockConfigurationReader {

  private final String directory = "data/blockfiles/";

  public void readInBlocks(int level, GamePiece[][] gridOfGamePieces) {
    String filePath = directory + "level" + level + ".txt";
    try {
      Scanner scanner = new Scanner(new File(filePath));
      int row = 0;
      String movement = scanner.nextLine();
      while (scanner.hasNextLine()) {
        String[] blockLives = scanner.nextLine().split(" ");
        createBlock(gridOfGamePieces, blockLives, movement, row, level);
        row++;
      }
    } catch (Exception e) {
      throw new RuntimeException("Invalid Block Configuration File!");
    }
  }

  private void createBlock(GamePiece[][] gridOfGamePieces, String[] blockLives, String movement, int row, int level) {
    for (int i = 0; i < gridOfGamePieces[row].length; i++) {
      double xPos = i * (getBlockWidth(level) + GameStatus.GAP) + GameStatus.GAP;
      double yPos = row * (getBlockHeight(level) + GameStatus.GAP) + GameStatus.GAP
          + GameStatus.DISPLAYHEIGHT;
      gridOfGamePieces[row][i] = new Block(xPos, yPos, getBlockWidth(level),
          getBlockHeight(level),
          Integer.parseInt(blockLives[i]));
      gridOfGamePieces[row][i].setMovement(movement, row);
    }
  }

  public GamePiece[][] loadLevel(Group root, int level) {
    GamePiece[][] gridOfGamePieces = new GamePiece[getRowNum(level)][getColNum(level)];
    readInBlocks(level, gridOfGamePieces);
    for (int i = 0; i < gridOfGamePieces.length; i++) {
      for (int j = 0; j < gridOfGamePieces[i].length; j++) {
        Block block = (Block) gridOfGamePieces[i][j]; // all game pieces are currently blocks
        if (block.getLives() > 0) {
          block.setId("block" + i + j);
          root.getChildren().add(block);
        }
      }
    }
    return gridOfGamePieces;
  }

  private int getRowNum(int level) {
    try {
      String filePath = directory + "level" + level + ".txt";
      Scanner scanner = new Scanner(new File(filePath));
      int numOfRows = 0;
      scanner.nextLine();
      while (scanner.hasNextLine()) {
        numOfRows++;
        scanner.nextLine();
      }
      return numOfRows;
    } catch (Exception e) {
      System.out.println("Invalid Block Configuration File!");
      return -1;
    }
  }

  private int getColNum(int level) {
    try {
      String filePath = directory + "level" + level + ".txt";
      Scanner scanner = new Scanner(new File(filePath));
      scanner.nextLine();
      String[] columns = scanner.nextLine().split(" ");
      return columns.length;
    } catch (Exception e) {
      System.out.println("Invalid Block Configuration File!");
      return -1;
    }
  }

  private double getBlockWidth(int level) {
    return (GameStatus.WINDOWWIDTH - (getColNum(level) + 1) * GameStatus.GAP) / (double) getColNum(
        level);
  }

  private double getBlockHeight(int level) {
    return ((double) GameStatus.WINDOWHEIGHT / 2.5 - (getRowNum(level) + 1) * GameStatus.GAP)
        / (double) getRowNum(level);
  }

  public int getFileCount() {
    File folder = new File(directory);
    return Objects.requireNonNull(folder.list()).length;
  }
}

