package ui;

import puzzle.Game;
import puzzle.GridRectangle;
import puzzle.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PuzzleFrame extends JFrame {
    private static final int HEIGHT = 850;
    private static final int WIDTH = 1500;

    PuzzleCanvas canvas;
    GameMenu gameMenu;
    Game currentGame;
    TimerPanel timerPanel;

    private boolean gameInProgress;

    public PuzzleFrame() throws IOException {
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.GRAY);
        JToggleButton imageButton = new JToggleButton("Preview");

        timerPanel = new TimerPanel(1000);

        controlPanel.add(imageButton);
        controlPanel.add(timerPanel);

        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setBackground(Color.GRAY);
        canvas = new PuzzleCanvas("./images/background.jpg");

        gameMenu = new GameMenu(this);
        gameMenu.setBackground(Color.GRAY);

        add(gameMenu, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenDim.width / 12, screenDim.height / 12);
        setSize(screenDim.width / 6 * 5, screenDim.height / 6 * 5);
        setVisible(true);
    }

    public void startNewGame(Game game) {
        currentGame = game;
        BufferedImage puzzleImage = currentGame.getImage();
        int w = puzzleImage.getWidth();

        canvas.setWholePiece(new Piece(getWidth()/2 + 20, 20, canvas, puzzleImage, -1));
        canvas.removeAllPieces();

        makePieces();
        repaint();
        timerPanel.startTimer(currentGame.getComplexity().getDuration());
    }

    public void makePieces() {


        BufferedImage image = (BufferedImage) currentGame.getImage();

        int rowCount = currentGame.getComplexity().getRowCount();
        int colCount = currentGame.getComplexity().getColCount();
        int width = image.getWidth(canvas);
        int height = image.getHeight(canvas);

        int pieceWidth = width / colCount;
        int pieceHeight = height / rowCount;

        List<Piece> pieces = null;
        pieces = new ArrayList<>(rowCount * colCount);
        int x;
        int y = 0;
        int order = 0;

        Piece currentPiece = null;
        for (int i = 0; i < rowCount; i++) {
            x = 0;
            for (int j = 0; j < colCount; j++) {
                BufferedImage subimage = image.getSubimage(x, y, pieceWidth, pieceHeight);
                Point pieceLocation = canvas.getRandomPlaceOnTable();
                currentPiece = new Piece(pieceLocation.x, pieceLocation.y, canvas, subimage, order++);
                canvas.add(currentPiece);
                pieces.add(currentPiece);
                x += pieceWidth;
            }
            y += pieceHeight;
        }
        GridRectangle gridField = new GridRectangle(20, 20, width, height, canvas, currentGame.getComplexity());
        canvas.setGridField(gridField);

    }



    private static BufferedImage scaleImage1(BufferedImage source, int desiredWidth, int desiredHeight){
        BufferedImage image = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.drawImage(source, 0, 0, desiredWidth, desiredHeight, null);

        return image;
    }


    public void loadGame(File filename) {
//        currentGame = new Game(filename);
//        puzzleArea.remove(puzzleGrid);
//        puzzleGrid = new JPanel();
//        puzzleGrid.setLayout(new GridLayout());
    }

    public void removeAll() {

    }

    public static void main(String[] args) {
        try {
            new PuzzleFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public class MyJPanel extends JPanel
//    {
//        public int x = 1000;		//CountDown from 100
//        public int delay = 1000; 	//milliseconds
//
//        MyJPanel()
//        {
//            ActionListener counter = new ActionListener() {
//                public void actionPerformed(ActionEvent evt)
//                {
//                    repaint();
//                    x--;
//                }};
//            new TimerPanel(delay, counter).start();
//        }
//
//        public void paintComponent(Graphics g)
//        {
//            super.paintComponent(g);
//            g.drawString(""+x,20,20);
//        }
//
//    }


}
