package puzzle;

import javax.swing.*;
import java.awt.*;


public class Player {

    private String name;
    private int score;
    private String playerImage = "/res/images/boy.jpg";

    public Player(String name, int score) {

        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void update() {

    }

    public void draw(Graphics2D g2D) {
    //  g2D.drawImage(getPlayerImage());
    }

    public Image getPlayerImage() {
        ImageIcon i = new ImageIcon(getClass().getResource(playerImage));
        return i.getImage();
    }
}
