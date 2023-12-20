package player;

import main.GamePanel;
import guns.Bullet;

import java.awt.*;

public class Player {

  @SuppressWarnings("unused")
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

  public boolean shoot;

  public boolean doubleJumped = false;

  public int jumpCounter = 0;

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

    if (y >= 600) {
      ySpeed = 0;
      y = 600;
      jumpCounter = 0;
    } else {
      ySpeed += 0.7;
    }
  
    if (keyUp && jumpCounter < 2) {
      jumpCounter++;
      ySpeed = -12.5;
      keyUp = false;
    }

    x += xSpeed;
    y += ySpeed;

    shoot();
    collision();

    hitbox.x = x;
    hitbox.y = y;
  }

  public void collision(){
    if (y < 0){
      y = 0;
    } else if (y > 600){
      y = 600;
    }
  }

  public void shoot(){
    if (this.shoot){
      System.out.println("shoot");
      this.shoot = false;  
    }
  }
}