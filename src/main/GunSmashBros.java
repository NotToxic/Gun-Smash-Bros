package main;

import java.awt.event.*;

import javax.swing.*;

import player.ssmHandler;
//你好
import ssm.SuperSocketMaster;
import ui.DisplayPanel;


public class GunSmashBros extends JFrame implements ActionListener{

  public DisplayPanel displayPanel = new DisplayPanel(this, ssm);

  Timer gameTimer = new Timer(1000/60, this);

  String previousMsg = "";

  public static SuperSocketMaster ssm;
  public static ssmHandler ssmh;

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == gameTimer) {
      displayPanel.repaint();
    } 
    
    else if (e.getSource() == ssm){
      displayPanel.gamePanel.ssmh.getData();
    } 
    
    else if (e.getSource() == displayPanel.connectPanel.hostButton){
      try {
        ssmHandler.hostMode(displayPanel, this, Integer.parseInt(displayPanel.connectPanel.portField.getText()));
        displayPanel.connectPanel.hostMode();
      } catch (NumberFormatException ex) {
        System.out.println("Please enter a port number");
      } 
    } 
    
    else if (e.getSource() == displayPanel.connectPanel.joinButton){
      try{
        ssmHandler.clientMode(displayPanel, this, Integer.parseInt(displayPanel.connectPanel.portField.getText()), displayPanel.connectPanel.ipField.getText());
        displayPanel.connectPanel.cilentMode();
      } catch (NumberFormatException ex){
        System.out.println("Please enter a port number and IP addess");
      }
    } 
    
    else if (e.getSource() == displayPanel.connectPanel.disconnectButton){
      ssmHandler.disconnect();
      displayPanel.connectPanel.disconnect();
    }

    else if (e.getSource() == displayPanel.connectPanel.gameButton1){
      displayPanel.gamePanel.strMap = displayPanel.gamePanel.loadMap("CPTMap1.csv");
      displayPanel.connectPanel.gameButton1.setEnabled(false);
    } 

    else if (e.getSource() == displayPanel.connectPanel.gameButton2){
      displayPanel.gamePanel.strMap = displayPanel.gamePanel.loadMap("CPTMap2.csv");
      displayPanel.connectPanel.gameButton2.setEnabled(false);
    } 
  }

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
