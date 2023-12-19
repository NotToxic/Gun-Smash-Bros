package main;

import java.awt.event.*;

import javax.swing.*;

public class GunSmashBros extends JFrame implements ActionListener {

  GamePanel gamePanel = new GamePanel();
  ConnectPanel connectPanel = new ConnectPanel();
  StartPanel startPanel = new StartPanel();

  Timer gameTimer = new Timer(1000/60, this);

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == gameTimer) {
      gamePanel.repaint();
    }
    if (e.getSource() == startPanel.playButton){
      this.setVisible(false);
      this.setContentPane(gamePanel);
      this.setVisible(true);
      gamePanel.repaint();
    }
    if (e.getSource() == startPanel.connectButton){
      this.setVisible(false);
      this.setContentPane(connectPanel);
      this.setVisible(true);
      connectPanel.repaint();
    }
    if (e.getSource() == gamePanel.backButton || e.getSource() == connectPanel.backButton){
      this.setContentPane(startPanel);
      startPanel.repaint();
    }
  }

  public GunSmashBros() {
    setContentPane(startPanel);
    startPanel.playButton.addActionListener(this);
    startPanel.connectButton.addActionListener(this);
    gamePanel.backButton.addActionListener(this);
    connectPanel.backButton.addActionListener(this);
    connectPanel.hostButton.addActionListener(this);
    connectPanel.joinButton.addActionListener(this);
    
    pack();
    setTitle("heheha");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    gameTimer.start();
  }

  public static void main(String[] args) {
    new GunSmashBros();
  }
}
