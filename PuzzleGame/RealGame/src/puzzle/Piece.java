package puzzle;

import figure.FigureCanvas;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Piece extends figure.Rectangle {

    private BufferedImage image;
    private int order;
//    private RowColumnOrder order;

    public Piece(int x, int y,  FigureCanvas canvas, BufferedImage image, int order) {
        this(x, y, image.getWidth(), image.getHeight(), canvas, image, order);
    }
    public Piece(int x, int y, int width, int height, FigureCanvas canvas, BufferedImage image, int order) {
        super(x, y, width, height, canvas);
        this.image = image;
        this.order = order;
    }

    public Piece(int x, int y, int width, int height, FigureCanvas canvas, Color color,  BufferedImage image, int order) {
        super(x, y, width, height, canvas, color);
        this.image = image;
        this.order = order;
    }
//    public Piece(int x, int y,  FigureCanvas canvas, BufferedImage image, RowColumnOrder order) {
//        this(x, y, image.getWidth(), image.getHeight(), canvas, image, order);
//    }
//    public Piece(int x, int y, int width, int height, FigureCanvas canvas, BufferedImage image, RowColumnOrder order) {
//        super(x, y, width, height, canvas);
//        this.image = image;
//        this.order = order;
//    }
//
//    public Piece(int x, int y, int width, int height, FigureCanvas canvas, Color color,  BufferedImage image, RowColumnOrder order) {
//        super(x, y, width, height, canvas, color);
//        this.image = image;
//        this.order = order;
//    }
//


    @Override
    public void draw(Graphics g) {
        g.drawImage(image, getX(), getY(), getWidth(), getHeight(), getCanvas());
    }

//    public RowColumnOrder getOrder() {
//        return order;
//    }
//
//    public void setOrder(RowColumnOrder order) {
//        this.order = order;
//    }


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public static class RowColumnOrder {
        int row;
        int col;

        public RowColumnOrder(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }




}
