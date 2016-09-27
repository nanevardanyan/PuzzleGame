package puzzle;

import figure.FigureCanvas;
import figure.Rectangle;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GridRectangle extends Rectangle {

    private static final Color DEFAULT_COLOR = new Color(244, 240, 201);

    private Color lineColor = new Color(35, 70, 45);
    private int rowCount;
    private int colCount;

    List<Piece> gridPieces = new ArrayList<>();
    Point [] vertexes;

    public GridRectangle(int x, int y, int width, int height, FigureCanvas canvas, Game.Complexity complexity) {
        this(x, y, width, height, canvas, DEFAULT_COLOR, complexity);
    }

    public GridRectangle(int x, int y, int width, int height, FigureCanvas canvas, Color color, Game.Complexity complexity) {
        super(x, y, width, height, canvas, color);
        init(complexity);
    }

    private void init (Game.Complexity complexity) {
        rowCount = complexity.getRowCount();
        colCount = complexity.getColCount();

        int rowsDistance = getHeight() / rowCount;
        int colsDistance = getWidth() / colCount;

        int order = 0;
        int x;
        int y = getY();
        vertexes = new Point[rowCount * colCount];
        for (int i = 0; i < rowCount; i++) {
            x = getX();
            for (int j = 0; j < colCount; j++) {
                vertexes[order++] = new Point(x, y);
                x += colsDistance;
            }

            y += rowsDistance;
        }
    }

    public boolean checkPiece( Piece piece){
        Point currVertex = vertexes[piece.getOrder()];
        return piece.getX() >= currVertex.x - 30 && piece.getX() <= currVertex.x + 50
                && piece.getY() >= currVertex.y - 30 && piece.getY() <= currVertex.y + 50;
    }

    public Point getExactVertex(Piece piece){
        return vertexes[piece.getOrder()];
    }

    public void addPiece(Piece piece){
        gridPieces.add(piece);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        g2D.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.8f));
        super.draw(g);
        drawVerticalLines(g);

        for (Piece piece : gridPieces) {
            piece.draw(g);
        }

        g2D.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1.f));


    }
    private void drawVerticalLines(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(lineColor);
        int rowsDistance = getHeight() / rowCount;
        int colsDistance = getWidth() / colCount;
        int x1 = getX();
        int x2 = getX() + getWidth();
        for (int i = 0; i <= rowCount; i++) {
            int y = getY() + i * rowsDistance;
            g.drawLine(x1, y, x2,  y);

        }
        int y1 = getY();
        int y2 = getY() + getHeight();
        int x = getX();
        for (int i = 0; i <= colCount; i++) {
            g.drawLine(x, y1, x,  y2);
            x += colsDistance;
        }
    }
}
