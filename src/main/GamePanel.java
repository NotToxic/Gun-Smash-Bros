package main;

import java.awt.*;
import javax.swing.*;

import inputs.KeyInputs;
import player.Player;

public class GamePanel extends JPanel{

  Player player = new Player(300, 500, 45, 90, this);

  JButton backButton = new JButton("Back");

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 1280, 720);

    g2d.setColor(Color.BLACK);
    player.move();
    
    g2d.fillRect(player.x, player.y, player.width, player.height);
  }

  public GamePanel() {
    setPreferredSize(new Dimension(1280, 720));
    SwingUtilities.invokeLater(() -> {
      addKeyListener(new KeyInputs(player));
      setFocusable(true);
      requestFocusInWindow();
    });
    backButton.setSize(200,200);
    backButton.setLocation(100,100);
    add(backButton);
  }
}
