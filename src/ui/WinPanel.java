package ui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**JPanel class that shows who won */
public class WinPanel extends JPanel{

    /**Display panel to swap through JPanels */
    public DisplayPanel displayPanel;
    /**Timer that lasts for three seconds */
    public Timer winTimer;

    /**Label that displays who won */
    JLabel winLabel = new JLabel("", JLabel.CENTER);

    /**Setting the text of who won on the winPanel
     * @param winner either "client" or "host", which changes the display of the winLabel
     */
    public void resetScreen(String winner) { 
        winLabel.setText(winner + " Victory!");
    }

    /**Setting the text of who won on the winPanel
     * @param winner either "client" or "host"
     */
    public void victory(String winner){
        displayPanel.changePanel("win");
        resetScreen(winner);
        winTimer.start();
      }
    
      /**Restarting the game
       * Respawns players
       * Sets their lives to 5 again
       * Changes the display panel to "game"
       * Stops the winTimer
       */
    public void resetGame(){
        displayPanel.gamePanel.player1.lives = 5;
        displayPanel.gamePanel.player1.respawn();
        displayPanel.gamePanel.player2.lives = 5;
        displayPanel.gamePanel.player2.respawn();
        displayPanel.changePanel("game");
        winTimer.stop();
    }

    /**Constructor for the winPanel
     * @param displayPanel adds the winPanel to the chain of other JPanels linked to displayPanel
     * @param listener listens for actions, specically used for the Timer
     */
    public WinPanel(DisplayPanel displayPanel, ActionListener listener){
        this.displayPanel = displayPanel;
        winTimer = new Timer(3000, listener);
        
        winLabel.setSize(500, 90);
        winLabel.setLocation(390, 160);

        SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            requestFocusInWindow();
            setLayout(null);
        });
        setPreferredSize(new Dimension(1280,720));


        add(winLabel);
        winTimer.addActionListener(listener);

    }
    
}
