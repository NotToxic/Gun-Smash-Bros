package player;

import main.GamePanel;
import guns.Bullet;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.Timer;

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

  public String direction = "right";

  public boolean shoot;
  public int shootTimer = 10;

  public boolean doubleJumped = false;

  public boolean dead = false;
  public int deathTimer = 90;

  public int jumpCounter = 0;

  private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

  public Player(int x, int y, int width, int height, GamePanel gamePanel) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.hitbox = new Rectangle(x, y, width, height);
    this.gamePanel = gamePanel;
  }

  public void move(String[][] map) {
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

    if (xSpeed > 8) xSpeed -= 1;
    if (xSpeed < -8) xSpeed += 1;

    ySpeed += 0.7;
  
    if (keyUp && jumpCounter < 2) {
      jumpCounter++;
      ySpeed = -12.5;
      keyUp = false;
    }
    if (keyDown && platform(map, x, y) == 0){
      y += 6;
      ySpeed = 2.25;
      jumpCounter = 1;
      if (ySpeed != 0){
      }
    } 
    
    x += xSpeed;
    y += ySpeed;

    if (shoot && shootTimer == 0){
      shoot();
    } else if (shootTimer != 0){
      shootTimer -= 1;
    }
    
    collision(map);

    hitbox.x = x;
    hitbox.y = y;
  }

  public void collision(String[][] map){
    if (y > 800){
      dead = true;
    }

    try{
      for (int i = 0; i < GamePanel.bulletList.size(); i++){
        Bullet b = (Bullet)GamePanel.bulletList.get(i);
        if (b.getX() > x && b.getX() < x+45 && b.getY() > y && b.getY() < y+90){
          if (b.getDirection().equals("left")){
            xSpeed -= 20;
          } else if (b.getDirection().equals("right")){
            xSpeed += 20;
          }
          System.out.println("hit");
          GamePanel.bulletList.remove(i);
        }
      }
    } catch (NullPointerException e){}
    

    // Variable to see how far away the player is from the platform
    int platformDistance = platform(map, x, y);
    if (platformDistance == 0 && ySpeed > 0){
      ySpeed = 0;
      jumpCounter = 0;
    } else if (platformDistance > 0 && ySpeed > 0){
      if (platformDistance < ySpeed){
        ySpeed = 0;
        jumpCounter = 0;
        y += platformDistance;
      }
    }
  }

  public int platform(String[][] map, int x, int y){
    try{
      for (int i = 0; i < 6; i++){
        if (map[(y+90+(8*i))/8][x/8].equals("p") || map[(y+90+(8*i))/8][(x+45)/8].equals("p")){
          return i*8;
        }
      }
      return -1;
    } catch (IndexOutOfBoundsException e){
      return -1;
    }
  }

  public void shoot(){
    if (direction.equals("right")){
      Bullet b = new Bullet(x + 45, y + 45, direction);
      bullets.add(b);
    } else if (direction.equals("left")){
      Bullet b = new Bullet(x, y + 45, direction);
      bullets.add(b);
    }
    shootTimer = 10;
    shoot = false;
  }

  public ArrayList getBulletList(){
    return bullets;
  }
  
  public boolean getDead(){
    return dead;
  }

  public void respawn(){
    x = 750;
    y = -100;
    ySpeed = 0;
    deathTimer = 90;
    dead = false;
  }
}