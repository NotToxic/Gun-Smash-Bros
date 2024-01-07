package inputs;

import player.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputs implements KeyListener {

  private Player player;
  public KeyInputs(Player player) {
    this.player = player;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Unused
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    
    if (key == KeyEvent.VK_W) {
      player.keyUp = true;
    }
    if (key == KeyEvent.VK_S) {
      player.keyDown = true;
    }
    if (key == KeyEvent.VK_D) {
      player.keyRight = true;
      player.direction = "right";
    }
    if (key == KeyEvent.VK_A) {
      player.keyLeft = true;
      player.direction = "left";
    }
    if (key == KeyEvent.VK_J) {
      player.shoot = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    
    if (key == KeyEvent.VK_W) {
      player.keyUp = false;
    }
    if (key == KeyEvent.VK_S) {
      player.keyDown = false;
    }
    if (key == KeyEvent.VK_D) {
      player.keyRight = false;
    }
    if (key == KeyEvent.VK_A) {
      player.keyLeft = false;
    }
    if (key == KeyEvent.VK_J) {
      player.shoot = false;
    }
  }
}
