package main;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import inputs.KeyInputs;
import player.Player;
import ui.DisplayPanel;
import ui.UIButton;

public class GamePanel extends JPanel{

  DisplayPanel displayPanel;
  Player player = new Player(300, 500, 45, 90, this);

  UIButton backButton;

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

  public GamePanel(DisplayPanel displayPanel) {

    this.displayPanel = displayPanel;
    backButton = new UIButton("BACK", "menu", displayPanel);

    setPreferredSize(new Dimension(1280, 720));

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
          requestFocusInWindow();
      }
    });

    SwingUtilities.invokeLater(() -> {
      addKeyListener(new KeyInputs(player));
      setFocusable(true);
      requestFocusInWindow();
      setLayout(null);
    });
    backButton.setSize(200,200);
    backButton.setLocation(100,100);
    add(backButton);
  }
}
