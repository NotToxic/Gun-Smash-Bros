package ui;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.GunSmashBros;
import player.Player;

public class WinPanel extends JPanel{

    public DisplayPanel displayPanel;
    public Timer deadTimer;

    JLabel winLabel = new JLabel("", JLabel.CENTER);

    public void resetScreen(String winner) { 
        winLabel.setText(winner + " Victory!");
    }

    public void victory(String winner){
        displayPanel.changePanel("win");
        resetScreen(winner);
        deadTimer.start();
      }
    
    public void resetGame(){
        displayPanel.gamePanel.player1.lives = 5;
        displayPanel.gamePanel.player1.respawn();
        displayPanel.gamePanel.player2.lives = 5;
        displayPanel.gamePanel.player2.respawn();
        displayPanel.changePanel("game");
        deadTimer.stop();
    }

    public WinPanel(DisplayPanel displayPanel, ActionListener listener){
        this.displayPanel = displayPanel;
        deadTimer = new Timer(3000, listener);
        
        winLabel.setSize(500, 90);
        winLabel.setLocation(390, 160);

        SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            requestFocusInWindow();
            setLayout(null);
        });
        setPreferredSize(new Dimension(1280,720));


        add(winLabel);
        deadTimer.addActionListener(listener);

    }
    
}
