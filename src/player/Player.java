package src.player;

import src.main.GamePanel;

import java.awt.*;
import javax.swing.*;

public class Player {

  private GamePanel gamePanel;

  public int x;
  public int y;
  public int width;
  public int height;
  Rectangle hitbox;

  double xSpeed;
  double ySpeed;

  public boolean keyRight;
  public boolean keyLeft;
  public boolean keyUp;
  public boolean keyDown;

  public boolean doubleJumped = false;

  public Player(int x, int y, int width, int height, GamePanel gamePanel) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.hitbox = new Rectangle(x, y, width, height);
    this.gamePanel = gamePanel;
  }

  public void move() {
    if (keyLeft && keyRight || !keyLeft && !keyRight) {
      xSpeed *= 0.7;
    }
    else if (keyRight) {
      xSpeed ++;
    }
    else if (keyLeft) {
      xSpeed --;
    }

    if (xSpeed > 0 && xSpeed < 0.75) {
      xSpeed = 0;
    }
    if (xSpeed < 0 && xSpeed > -0.75) {
      xSpeed = 0;
    }

    if (xSpeed > 8) xSpeed = 8;
    if (xSpeed < -8) xSpeed = -8;

    if (y > 600) {
      ySpeed = 0;
      if (keyUp) {
        ySpeed = -7;
      }
    }

    ySpeed += 0.3;

    x += xSpeed;
    y += ySpeed;

    hitbox.x = x;
    hitbox.y = y;
  }
}