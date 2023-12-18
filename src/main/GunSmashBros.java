package src.main;

import java.awt.*;
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
    if (e.getSource() == startPanel.play){
      this.setVisible(false);
      this.setContentPane(gamePanel);
      this.setVisible(true);
    }
    if (e.getSource() == startPanel.connect){
      this.setContentPane(connectPanel);
      connectPanel.repaint();
    }
  }

  public GunSmashBros() {
    setContentPane(startPanel);

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
