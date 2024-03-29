// By Thomson, Pranay and Terry
// Janurary 25, 2024
// A real-time game where players must knock each other off the map by hitting each other with bullets, just like SuperSmashbros

package main;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import player.ssmHandler;
import ssm.SuperSocketMaster;
import ui.DisplayPanel;

//Main Program

//* This class is the main class, and is where all the repaint and SuperSocketMaster is handled */
public class GunSmashBros extends JFrame implements ActionListener{

  /**General Display Panel to contain other panels*/
  public DisplayPanel displayPanel = new DisplayPanel(this, ssm); 

  /**Refresh rate of the game @ 60 FPS*/
  Timer gameTimer = new Timer(1000/60, this);

  /**SSM implementation for opponenet movement, and bullet movement*/
  public static SuperSocketMaster ssm;
  /**SSM for all the chats and crates*/
  public static SuperSocketMaster ssm2;


  @Override
  /**To read any data being sent in and out, and to refresh the diplayPanel 
   * @param e for any actionEvents
  */
  public void actionPerformed(ActionEvent e) {
    /**Refresh the panel. */
    if (e.getSource() == gameTimer) {
      displayPanel.repaint();
    } 
    /**Restart the game after the winPanel has been shown for a period of time. */
    if (e.getSource() == displayPanel.winPanel.winTimer){
      displayPanel.winPanel.resetGame();
    }
    
    /**Obtain chat and crate data. */
    else if (e.getSource() == ssm2){
      displayPanel.gamePanel.ssmh.getOtherData();
    } 
    
    /**Host Button Functionality - Hosting Game in Device*/
    else if (e.getSource() == displayPanel.connectPanel.hostButton){
      try {
        ssmHandler.hostMode(displayPanel, this, Integer.parseInt(displayPanel.connectPanel.portField.getText()));
        displayPanel.connectPanel.hostMode();
        displayPanel.connectPanel.gameButton2.setEnabled(true);
        displayPanel.connectPanel.gameButton2.setEnabled(true);
      } catch (NumberFormatException ex) {
        System.out.println("Please enter a port number");
      } 
    } 
    
    /**Join Button Functionality - Joining Game in Device*/
    else if (e.getSource() == displayPanel.connectPanel.joinButton){
      try{
        ssmHandler.clientMode(displayPanel, this, Integer.parseInt(displayPanel.connectPanel.portField.getText()), displayPanel.connectPanel.ipField.getText());
        displayPanel.connectPanel.cilentMode();
      } catch (NumberFormatException ex){
        System.out.println("Please enter a port number and IP addess");
      }
    } 
    
    /**Disconnecting Functionality - If Disconnect Button is pressed*/
    else if (e.getSource() == displayPanel.connectPanel.disconnectButton){
      ssmHandler.disconnect();
      displayPanel.connectPanel.disconnect();
    }

    /**Button Action to Choose The Map That Will Be Used*/
    /**Button For Map1*/
    else if (e.getSource() == displayPanel.connectPanel.gameButton1){
      displayPanel.gamePanel.strMapName = "CPTMap1";
      displayPanel.gamePanel.loadMap("CPTMap1");
      displayPanel.connectPanel.gameButton1.setEnabled(false);
      displayPanel.connectPanel.gameButton2.setEnabled(true);
    } 

    /**Button For Map2*/
    else if (e.getSource() == displayPanel.connectPanel.gameButton2){
      displayPanel.gamePanel.strMapName = "CPTMap2";
      displayPanel.gamePanel.loadMap("CPTMap2");
      displayPanel.connectPanel.gameButton2.setEnabled(false);
      displayPanel.connectPanel.gameButton1.setEnabled(true);
    } 
  }

  /**Main Content Frame*/
  public GunSmashBros() {
    setContentPane(displayPanel);
    
    pack();
    setTitle("GunSmashBros");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    gameTimer.start();
  }

  /**Constructor for main*/
  public static void main(String[] args) {
    new GunSmashBros();
  }
}
