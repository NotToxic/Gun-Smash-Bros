package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;

import inputs.KeyInputs;
import player.Player;

public class TutorialPanel extends GraphicsPanel {

  public Player dummy = new Player(500, -100, 45, 90, this);
  private int intTutorialStage = 0;

  JLabel tutorialText = new JLabel("", JLabel.CENTER);

  public void tutorialStage() {
    if (intTutorialStage == 0) {
      tutorialText.setText("Press W, A, D, to move up, left, right");
    } 
    else if (intTutorialStage == 1) {
      tutorialText.setText("Press S to go down platforms");
    } 
    else if (intTutorialStage == 2) {
      tutorialText.setText("Press J to shoot guns");
    } 
    else if (intTutorialStage == 3) {
      if (player1.gun.gunName.equals("heavyGuy")) {
        intTutorialStage = 4;
      }
      tutorialText.setText("Pick up a crate for a new gun");
      if (player1.gun.gunName.equals("lightGuy")){
        spawnCrates("heavyGuy", 500);
      }
    } 
    else if (intTutorialStage == 4) {
      tutorialText.setText("Shoot the enemy off the platform");
      dummy.move(strArrayMap);
      if (dummy.getDead()) {
        intTutorialStage = 5;
      }
    } 
    else if (intTutorialStage == 5) {
      tutorialText.setText("Congrats, you have completed the tutorial");
    } 
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    player1.move(strArrayMap);
    
    paintMap(strArrayMap, g2d, imgMap);
    drawPlayer(g2d, player1);
    drawGun(g2d, player1);
    drawPlayer(g2d, dummy);
    drawBullets(g2d);
    drawCrates(g2d);

    tutorialStage();

    if(player1.dead) {
      player1.respawn();
    }
  }

  public TutorialPanel(DisplayPanel displayPanel) {
    super(displayPanel);

    tutorialText.setSize(500, 260);
    tutorialText.setLocation(0,420);
    add(tutorialText);

    addKeyListener(new KeyInputs(player1));
    loadMap("TutorialMap");
    addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (intTutorialStage == 0) {
          if (key == KeyEvent.VK_W || key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            intTutorialStage = 1;
          }
        } 
        else if (intTutorialStage == 1) {
          if (key == KeyEvent.VK_S) {
            intTutorialStage = 2;
          }
        } 
        else if (intTutorialStage == 2) {
          if (key == KeyEvent.VK_J) {
            intTutorialStage = 3;
          }
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }
      
    });
  }
}

