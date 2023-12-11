import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class gss implements ActionListener, KeyListener{
    // Properties
    JFrame theFrame = new JFrame("Game Smash Bros");
    newPanel thePanel = new newPanel();

    Timer theTimer = new Timer(1000/60, this);
    
    // Methods
    public void actionPerformed(ActionEvent evt){
        if (evt.getSource() == theTimer){
            thePanel.char1.outOfBounds();
            thePanel.repaint();
        }
    }

    public void keyPressed(KeyEvent evt){
        thePanel.char1.deflectionX(evt.getKeyChar(), false);
        thePanel.char1.deflectionY(evt.getKeyChar(), false);
    }
    public void keyReleased(KeyEvent evt){
        thePanel.char1.deflectionX(evt.getKeyChar(), true);
        thePanel.char1.deflectionY(evt.getKeyChar(), true);
    }
    public void keyTyped(KeyEvent evt){
    }


    // Constructor
    public gss(){
        thePanel.setLayout(null);
        thePanel.setPreferredSize(new Dimension(1280,720));

        theFrame.setContentPane(thePanel);
        theFrame.pack();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setResizable(false);
        theFrame.setVisible(true);
        theFrame.addKeyListener(this);

        theTimer.start();

    }

    // Main Method
    public static void main(String[] args){
        new gss();
    }
}