package puzzle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;


public class Game implements Serializable {

    private BufferedImage image;
    private Complexity complexity;

    public Game(File filename) {
        this(filename, Complexity.EASY);
    }

    public Game(File filename, Complexity complexity) {
        this.complexity = complexity;
        try {
            image = ImageIO.read(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public Complexity getComplexity() {
        return complexity ;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    public enum GameStatus{ NOT_STARTED, STARTED, PAUSED }

    public enum Complexity {
        EASY(3, 3, "easy", 10000), MEDIUM(5, 5, "medium", 5000), DIFFICULT(10, 10, "difficult", 2000);

        private final int rowCount;
        private final int colCount;
        private final String name;
        private final int duration;

        private Complexity(int rowCount, int colCount, String name, int timeCount) {
            this.rowCount = rowCount;
            this.colCount = colCount;
            this.name = name;
            this.duration = timeCount;
        }

        public int getDuration() {
            return duration;
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getColCount() {
            return colCount;
        }

        public static Complexity findByName(String name) {
            for (Complexity complexity : values()) {
                if (complexity.name.equals(name)) {
                    return complexity;
                }
            }
            return null;
        }
    }

}