package src.main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GunSmashBros extends JFrame implements ActionListener {

  GamePanel gamePanel = new GamePanel();
  Timer gameTimer = new Timer(1000/60, this);

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == gameTimer) {
      gamePanel.repaint();
    }
  }

  public GunSmashBros() {
    setContentPane(gamePanel);
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
