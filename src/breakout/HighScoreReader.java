package breakout;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class HighScoreReader {

    private final String filePath = "data/highscore/highscores.txt" ;

    public int readInHighScore(){
        try{
            Scanner scanner = new Scanner(new File(filePath));
            return Integer.parseInt(scanner.nextLine());
        }catch(Exception e){
            System.out.println("Invalid High Score File");
        }
        return -1;
    }

    public void replaceHighScore(int updatedHighScore) {
        try {
            FileWriter fileWriter = new FileWriter(new File(filePath), false);
            fileWriter.write(Integer.toString(updatedHighScore));
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Invalid High Score File");
        }
    }
}

