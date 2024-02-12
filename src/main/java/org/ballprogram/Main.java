    package org.ballprogram;
    import javax.swing.*;
    public class Main {
        public static void main(String[] args) {

            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JFrame frame = new JFrame("A Bouncing Ball");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setContentPane(new BouncingBallSimple()); // Corrected class name
                    frame.pack();
                    frame.setVisible(true);
                }
            });
        }
    }
