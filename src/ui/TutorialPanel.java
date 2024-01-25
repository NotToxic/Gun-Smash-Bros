package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import inputs.KeyInputs;
import player.Player;


public class TutorialPanel extends GraphicsPanel {

  public Player dummy = new Player(500, -100, 45, 90, this);

  public TutorialPanel(DisplayPanel displayPanel) {
    super(displayPanel);

    addKeyListener(new KeyInputs(player1));
    strArrayMap = loadMap("TutorialMap");
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    player1.move(strArrayMap);
    
    paintMap(strArrayMap, g2d, imgMap);
    drawPlayer(g, player1);
    drawGun(g, player1);
    drawBullets(g);
  }
}

