package ui;

import puzzle.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class GameMenu extends JMenuBar {
    PuzzleFrame frame;
    Game.Complexity complexityValue = Game.Complexity.EASY;

    private JCheckBoxMenuItem showTimer, easy, medium, difficult;

    private JPanel hintArea, hintArea2, puzzleArea, puzzleGrid;

    private int tiles = 9;
    private ArrayList pieces = new ArrayList();

    private JFileChooser fileChooser = new JFileChooser();
    //private Game currentGame;
    private boolean gameInProgress;

    boolean gameStarted = false;

    public GameMenu(PuzzleFrame frame) throws HeadlessException {
        this.frame = frame;

        JMenu fileMenu = new JMenu("File");
        fileMenu.setBackground(Color.GRAY);
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem loadGame = new JMenuItem("Load Game");
        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem quit = new JMenuItem("Exit");


        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameActionPerformed();
            }
        });
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGameActionPerformed();
            }
        });
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGameActionPerformed();
            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGameActionPerformed();
            }
        });

//        addMouseListener(new MouseHandler());
//        addMouseMotionListener(new MouseHandler());

        fileMenu.add(newGame);
        fileMenu.add(loadGame);
        fileMenu.add(saveGame);
        fileMenu.addSeparator();
        fileMenu.add(quit);

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        JMenu actionsMenu = new JMenu("Actions");
        actionsMenu.setBackground(Color.GRAY);
        easy = new JCheckBoxMenuItem("Easy");
        medium = new JCheckBoxMenuItem("Medium");
        difficult = new JCheckBoxMenuItem("Difficult");
        // actionsMenu.addActionListener(this);

        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyActionPerformed();
            }
        });
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediumActionPerformed();
            }
        });
        difficult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficultActionPerformed();
            }
        });

        JMenuItem choosePlayerImage = new JMenuItem("Choose a player");
        choosePlayerImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePlayerImageActionPerformed();
            }
        });
        actionsMenu.add(choosePlayerImage);


        JMenuItem scramble = new JMenuItem("Scramble");
        scramble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrambleGameActionPerformed();
            }
        });
        actionsMenu.add(scramble);

        JMenu complexity = new JMenu("Complexity");

        complexity.add(easy);
        complexity.add(medium);
        complexity.add(difficult);

        actionsMenu.add(complexity);

        add(fileMenu);
        add(actionsMenu);
    }

//        showTimer = new JCheckBoxMenuItem("Show TimerPanel");
//        showTimer.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                todo
//            }
//        });
//        actionsMenu.add(showTimer);


//        puzzleGrid.setSize(700, 600);

//        (puzzleArea = new JPanel()).setLayout(new FlowLayout());
//        puzzleArea.add(puzzleGrid);
//        puzzleArea.setBorder(new TitledBorder("Image Puzzle"));
//
    //    (hintArea = new JPanel()).setBorder(new TitledBorder("Preview"));
    //      hintArea.add(new SidePanel());
//
//        (hintArea2 = new JPanel()).setBorder(new TitledBorder("Pieces"));
//        hintArea2.add(new SidePanel());

//        c.add(puzzleArea, BorderLayout.CENTER);
//        c.add(hintArea, BorderLayout.EAST);
//        c.add(hintArea2,BorderLayout.SOUTH);

//    @Override
//    public void actionPerformed(ActionEvent e) {

    private void newGameActionPerformed() {
        int result = fileChooser.showOpenDialog(frame);
        if (result != JFileChooser.CANCEL_OPTION) {
            File filename = fileChooser.getSelectedFile();
            Game game = new Game(filename, complexityValue);
            frame.startNewGame(game);
        }
    }

    private void loadGameActionPerformed() {
        int result = fileChooser.showOpenDialog(frame);
        if (result != JFileChooser.CANCEL_OPTION) {
            ObjectInputStream in = null;
            try {
                File filename = fileChooser.getSelectedFile();

                BufferedImage bimg = ImageIO.read(filename);
//                currentGame = new Game(filename);

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    private void saveGameActionPerformed() {
        save();
    }

    private void quitGameActionPerformed() {
        int answer = -1;
        if (gameInProgress) answer = askSave();
        if (answer != JOptionPane.CANCEL_OPTION) {
            if (answer == JOptionPane.OK_OPTION) {
                save();
            } else {
                System.exit(0);
            }
        }
    }

    private void scrambleGameActionPerformed() {
        puzzleGrid.removeAll();
        Collections.shuffle(pieces);
        for (int i = 0; i < tiles; i++) {
            //      puzzleGrid.add(pieces);
            pieces.get(i);
            puzzleGrid.validate();
        }
    }

    private void choosePlayerImageActionPerformed() {

    }

    private void easyActionPerformed() {
        if (medium.isSelected() || difficult.isSelected()) {
            medium.setSelected(false);
            difficult.setSelected(false);
            setComplexity(Game.Complexity.EASY);
        } else {
            easy.setSelected(true);
        }
    }

    private void mediumActionPerformed() {
        if (easy.isSelected() || difficult.isSelected()) {
            easy.setSelected(false);
            difficult.setSelected(false);
        }
        setComplexity(Game.Complexity.MEDIUM);
        medium.setSelected(true);

    }

    private void difficultActionPerformed() {
        if (easy.isSelected() || medium.isSelected()) {
            medium.setSelected(false);
            easy.setSelected(false);
            setComplexity(Game.Complexity.DIFFICULT);
        } else
            difficult.setSelected(true);
    }

    private int askSave() {
        return JOptionPane.showConfirmDialog(frame, "The current game has not been saved, " +
                        "would you like to save it?", "Game In Progress",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

    }

    private void save() {
        int result = fileChooser.showSaveDialog(frame);
        if (result != JFileChooser.CANCEL_OPTION) {
            ObjectOutputStream out = null;
            try {
                File filename = fileChooser.getSelectedFile();
                out = new ObjectOutputStream(new FileOutputStream(filename));
                //    out.writeObject(currentGame);
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(frame, "Exception: Read Problem.");
            } finally {
                try {
                    if (out != null) out.close();
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(frame, "Exception: Can't close file.");
                }
            }
        }
    }

    public void setComplexity(Game.Complexity complexity) {
        complexityValue = complexity;
        if(frame.currentGame != null){
            frame.currentGame.setComplexity(complexity);
        }
    }
}
