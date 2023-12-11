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
            thePanel.repaint();
        }
    }

    public void keyPressed(KeyEvent evt){
        if (evt.getKeyChar() == 'w'){
            thePanel.intChar1Y -= 50;
        } 
        /*
         if (evt.getKeyChar() == 's'){
            thePanel.intChar1Y += 10;
        }
        */
       
        if (evt.getKeyChar() == 'd'){
            thePanel.intChar1X += 10;
        }
        if (evt.getKeyChar() == 'a'){
            thePanel.intChar1X -= 10;
        }
    }
    public void keyReleased(KeyEvent evt){
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