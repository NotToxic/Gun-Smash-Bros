package main;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import player.ssmHandler;
//你好
import ssm.SuperSocketMaster;
import ui.DisplayPanel;

//Main Progam

public class GunSmashBros extends JFrame implements ActionListener{

  //General Display Panel to contain other panels
  public DisplayPanel displayPanel = new DisplayPanel(this, ssm); 

  //Refresh Rate
  Timer gameTimer = new Timer(1000/60, this);

  String previousMsg = "";
  static String strName = null;

  //ssm for opponenet movement, and bullet movement
  public static SuperSocketMaster ssm;
  // ssm for all the chats and crates
  public static SuperSocketMaster ssm2;

  public static ssmHandler ssmh;

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == gameTimer) {
      displayPanel.repaint();
    } 
    if (e.getSource() == displayPanel.winPanel.deadTimer){
      displayPanel.winPanel.resetGame();
    }
    
    else if (e.getSource() == ssm2){
      displayPanel.gamePanel.ssmh.getOtherData();
    } 
    
    //Host Button Functionality - Hosting Game in Device
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
    
    //Join Button Functionality - Joining Game in Device
    else if (e.getSource() == displayPanel.connectPanel.joinButton){
      try{
        ssmHandler.clientMode(displayPanel, this, Integer.parseInt(displayPanel.connectPanel.portField.getText()), displayPanel.connectPanel.ipField.getText());
        displayPanel.connectPanel.cilentMode();
      } catch (NumberFormatException ex){
        System.out.println("Please enter a port number and IP addess");
      }
    } 
    
    //Disconnecting Functionality - If Disconnect Button is pressed
    else if (e.getSource() == displayPanel.connectPanel.disconnectButton){
      ssmHandler.disconnect();
      displayPanel.connectPanel.disconnect();
    }

    //Button Action to Choose The Map That Will Be Used
    //Button For Map1
    else if (e.getSource() == displayPanel.connectPanel.gameButton1){
      displayPanel.gamePanel.strMapName = "CPTMap1";
      displayPanel.gamePanel.loadMap("CPTMap1");
      displayPanel.connectPanel.gameButton1.setEnabled(false);
      displayPanel.connectPanel.gameButton2.setEnabled(true);
    } 

    //Button For Map2
    else if (e.getSource() == displayPanel.connectPanel.gameButton2){
      displayPanel.gamePanel.strMapName = "CPTMap2";
      displayPanel.gamePanel.loadMap("CPTMap2");
      displayPanel.connectPanel.gameButton2.setEnabled(false);
      displayPanel.connectPanel.gameButton1.setEnabled(true);
    } 
  }

  //Main Content Frame
  public GunSmashBros() {
    setContentPane(displayPanel);
    
    pack();
    setTitle("GunSmashBros");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    gameTimer.start();
  }

  public static void main(String[] args) {
    new GunSmashBros();
  }
}
