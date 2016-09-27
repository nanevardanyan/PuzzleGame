package ui;

import figure.Figure;
import figure.FigureCanvas;
import puzzle.GridRectangle;
import puzzle.Piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class PuzzleCanvas extends FigureCanvas {

    private BufferedImage backgroundImage;

    private GridRectangle gridField;
    private Piece wholePiece;

    public PuzzleCanvas( String backGroundImagName) throws IOException {
        //Background image
        InputStream is = getClass().getClassLoader().getResourceAsStream(backGroundImagName);
        backgroundImage = ImageIO.read(is);
    }
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }
    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public GridRectangle getGridField() {
        return gridField;
    }

    public void setGridField(GridRectangle gridField) {
        this.gridField = gridField;
    }

    public Piece getWholePiece() {
        return wholePiece;
    }

    public void setWholePiece(Piece wholePiece) {
        this.wholePiece = wholePiece;
    }

    public void removeAllPieces(){
       super.removeAllFigures();
    }

    @Override
    protected void mouseDraggedPerformed(MouseEvent e) {
        super.mouseDraggedPerformed(e);
    }

    protected void mouseReleasedPerformed(MouseEvent e) {
        if (isSelected()) {
            Piece selectedPiece = (Piece)getSelected();
            if(gridField.checkPiece(selectedPiece)){
                Point point = gridField.getExactVertex(selectedPiece);
                selectedPiece.setX(point.x);
                selectedPiece.setY(point.y);

            } else {
                Point point = getRandomPlaceOnTable();
                selectedPiece.setX(point.x);
                selectedPiece.setX(point.y);
            }
            repaint();

        }
        setSelected(false);
        setmX(e.getX());
        setmY(e.getY());
    }

    public Point    getRandomPlaceOnTable (){
        Random random = new Random();
        return new Point(getWidth()/2 + random.nextInt(100), getHeight()/2 + random.nextInt(100) + 100);
    }


    @Override
    public void paint(Graphics g) {

        g.drawImage(backgroundImage, 0, 0, this);

        if(gridField != null){
            gridField.draw(g);
        }
        if(wholePiece != null){
            wholePiece.draw(g);
        }

        for (Figure figure : figures) {
            figure.draw(g);
        }
    }
}



