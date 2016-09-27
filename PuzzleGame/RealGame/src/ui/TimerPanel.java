package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TimerPanel extends JPanel {

    JTextField textfield = new JTextField(15);
    private static int count = 500;
    javax.swing.Timer timer;

    public TimerPanel(int i) {
        add(textfield);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                if (count == 0) {
                    JOptionPane.showMessageDialog( null, "Your time is Up", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    timer.stop();
                }
                count -= 1;
                i = i + 3;
                textfield.setText("Time remaining : " + String.valueOf(count));
            }
        };

        timer = new javax.swing.Timer(1000, actionListener);
        setVisible(false);


    }

    public void startTimer(int count){
        this.count = count;
        setVisible(true);
        timer.start();
        if (count == 0) {
            timer.stop();
        }
    }


}
