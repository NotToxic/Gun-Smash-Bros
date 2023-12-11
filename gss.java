import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class gss implements ActionListener{
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

    // Constructor
    public gss(){
        thePanel.setLayout(null);
        thePanel.setPreferredSize(new Dimension(1200,800));

        theFrame.setContentPane(thePanel);
        theFrame.pack();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setResizable(false);
        theFrame.setVisible(true);

        theTimer.start();

    }

    // Main Method
    public static void main(String[] args){
        new gss();
    }
}