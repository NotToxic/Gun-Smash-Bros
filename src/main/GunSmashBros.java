package main;

import java.awt.event.*;

import javax.swing.*;

import ssm.SuperSocketMaster;
import ui.ChatPanel;
import ui.DisplayPanel;


public class GunSmashBros extends JFrame implements ActionListener{

  DisplayPanel displayPanel = new DisplayPanel(this, ssm);

  Timer gameTimer = new Timer(1000/60, this);
  
  public static SuperSocketMaster ssm = null;

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == gameTimer) {
      displayPanel.repaint();
    } else if (e.getSource() == ssm){

    } else if (e.getSource() == displayPanel.connectPanel.hostButton){
      try{
        displayPanel.connectPanel.host(this);
        displayPanel.gamePanel.playerControl(1);
        System.out.println("Start socket in server mode");
      } catch (NumberFormatException ex){
        System.out.println("Please enter a port number");
      }
        
    } else if (e.getSource() == displayPanel.connectPanel.joinButton){
      try{
        displayPanel.connectPanel.connect(this);
        displayPanel.gamePanel.playerControl(2);
        System.out.println("Start socket in join mode");
      } catch (NumberFormatException ex){
        System.out.println("Please enter a port number and IP addess");
      }
    } else if (e.getSource() == displayPanel.connectPanel.disconnectButton){
      displayPanel.connectPanel.disconnect();
    } 
    if(e.getSource() == ssm){
      ChatPanel.chatArea.append("Opponenet: "+ssm.readText() + "\n");
    }
  }

  public GunSmashBros() {
    setContentPane(displayPanel);
    
    pack();
    setTitle("heheh");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    gameTimer.start();
  }

  public static void main(String[] args) {
    new GunSmashBros();
  }
}
