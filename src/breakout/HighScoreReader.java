package breakout;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class HighScoreReader {

  private static String filePath = "data/highscore/highscores.txt";

  public int readInHighScore() {
    try {
      Scanner scanner = new Scanner(new File(filePath));
      return Integer.parseInt(scanner.nextLine());
    } catch (Exception e) {
      throw new RuntimeException("Invalid High Score File");
    }
  }

  public void replaceHighScore(int updatedHighScore) {
    try {
      FileWriter fileWriter = new FileWriter(new File(filePath), false);
      fileWriter.write(Integer.toString(updatedHighScore));
      fileWriter.close();
    } catch (Exception e) {
      throw new RuntimeException("Invalid High Score File");
    }
  }

  // testing purposes
  public static void setHighScoreFilePath(String filePath) {
    HighScoreReader.filePath = filePath;
  }
}

